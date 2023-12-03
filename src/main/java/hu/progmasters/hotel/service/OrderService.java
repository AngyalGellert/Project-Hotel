package hu.progmasters.hotel.service;

import hu.progmasters.hotel.domain.PurchaseOrder;
import hu.progmasters.hotel.domain.Reservation;
import hu.progmasters.hotel.domain.User;
import hu.progmasters.hotel.exception.OrderNotFoundException;
import hu.progmasters.hotel.repository.OrderRepository;
import hu.progmasters.hotel.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
public class OrderService {
    private static final String CONFIRM_URL = "https://i.imgur.com/dweSWCj.png";
    private final ReservationRepository reservationRepository;

    private OrderRepository orderRepository;
    private ReservationService reservationService;
    private UserService userService;

    private OauthTokenService oauthTokenService;
    private ModelMapper modelMapper;

    private PurchaseOrder purchaseOrder;

    public OrderService(OrderRepository orderRepository, ReservationService reservationService, UserService userService, OauthTokenService oauthTokenService, ModelMapper modelMapper,
                        ReservationRepository reservationRepository) {
        this.orderRepository = orderRepository;
        this.reservationService = reservationService;
        this.userService = userService;
        this.oauthTokenService = oauthTokenService;
        this.modelMapper = modelMapper;

        this.reservationRepository = reservationRepository;
    }

    public String createOrder(Long userId, List<Long> reservationIds) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        User user = userService.findUserById(userId);
//        List<Reservation> reservations = new ArrayList<>();

        for (int i = 0; i < reservationIds.size(); i++) {
            if (userService.containReservation(user, reservationIds.get(i))) {
                Reservation reservation = reservationService.findReservationById(reservationIds.get(i));
                purchaseOrder.getBasket().add(reservation);
            }
        }
        purchaseOrder.setUser(user);
//        cart.setBasket(new ArrayList<>(reservations));
//        cart.setBasket(reservations);
        purchaseOrder.setSumOrder(orderSum(purchaseOrder));
        purchaseOrder.setUniqueId(String.valueOf(UUID.randomUUID()));
        orderRepository.save(purchaseOrder);
        for (Reservation reservation : purchaseOrder.getBasket()) {
            reservation.setPurchaseOrder(purchaseOrder);
            reservationRepository.save(reservation);
        }
        String confirmUrl = "Localhost:8080/paypal/payment/confirm/"+ purchaseOrder.getUniqueId();
        return confirmUrl;
    }

    public String paymentOrder(Long orderId) {
        PurchaseOrder searchedOrder = findOrderById(orderId);
        String accessToken = oauthTokenService.getLastToken();
        String orderString = jsonString(searchedOrder);
        HttpPost request = new HttpPost("https://api-m.sandbox.paypal.com/v2/checkout/orders");
        request.setHeader("Authorization", "Bearer " + accessToken);
        StringEntity body = null;
        CloseableHttpResponse response = null;
        JSONObject responseObject = new JSONObject();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            body = new StringEntity(orderString);
            body.setContentType("application/json");
            if (body == null) {
                throw new RuntimeException("Not work paymentorder()");
            }
            request.setEntity(body);
            response = httpClient.execute(request);
            responseObject = responseEval(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        searchedOrder.setPaypalId(responseObject.getString("id"));
        orderRepository.save(searchedOrder);

        return " https://www.sandbox.paypal.com/checkoutnow?token=" + searchedOrder.getPaypalId();
    }

//    cancel method

    public PurchaseOrder findOrderById(Long orderId) {
        Optional<PurchaseOrder> result = orderRepository.findById(orderId);
        return result.orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    public double orderSum(PurchaseOrder purchaseOrder) {
        double sum = 0;
        for (Reservation reservation : purchaseOrder.getBasket()) {
            sum = sum + reservationService.sumRoom(reservation);
        }
        return sum;
    }


    public String listingItems(PurchaseOrder order) {
        String itemList = "";
        for (Reservation reservation : order.getBasket()) {
            String item = "{\"name\":\"" + " days in " + reservation.getRoom().getName() + " room." + "\",\"unit_amount\":{\"currency_code\":\"EUR\",\"value\":\""
                    + reservation.getRoom().getPricePerNight() + "\"},\"quantity\":\""
                    + reservationService.getReservationDays(reservation)
                    + "\",\"category\":\"DIGITAL_GOODS\"},";
            itemList = itemList + item;
        }
        itemList = itemList.substring(0, itemList.length() - 1);
        return itemList;
    }

    public String jsonString(PurchaseOrder order) {
        String listingItem = listingItems(order);
        String orderJson = "{\"intent\":\"CAPTURE\",\"purchase_units\":[{\"amount\":{\"currency_code\":\"EUR\",\"value\":\""
                + order.getSumOrder() + "\",\"breakdown\":{\"item_total\":{\"currency_code\":\"EUR\",\"value\":\""
                + order.getSumOrder() + "\"}}}" +
                ",\"items\":["
                + listingItem
                + "]" +
                " }],\"payee\":{\"email_address\":\"sb-n1ytq28370920@business.example.com\",\"merchant_id\":\"9BFVUU3JUE6L4\",\"display_data\":{\"brand_name\":\"Middle-earth Hotel\"}},\"application_context\":{\"user_action\":\"PAY_NOW\",\"brand_name\":\"Middle-earth Hotel\",\"cancel_url\":\"localhost:8080/paypal/payment/cancel/"
                + order.getUniqueId() + "\",\"return_url\":\"https://i.imgur.com/dweSWCj.png\",\"shipping_preference\":\"NO_SHIPPING\",\"payment_method\":{\"payer_selected\":\"PAYPAL\",\"payee_preferred\":\"IMMEDIATE_PAYMENT_REQUIRED\",\"allow_push_funding\":false}}}";

        return orderJson;
    }

    private JSONObject responseEval(CloseableHttpResponse response) {
        JSONObject responseObject = new JSONObject();
        if (response != null) {
            int statusCode = response.getStatusLine().getStatusCode();
            String responseString;
            try {
                responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                if (statusCode >= 400) {
                    throw new RuntimeException("Error while executing request, statusCode: " + statusCode + "message: " + responseString);
                }
                responseObject = new JSONObject(responseString);
                log.info("Paypal: {}", responseObject.getString("id"));
            } catch (IOException e) {
                log.error("", e);
            }
        }
        return responseObject;

    }

    public String confirmPayment(String uniqueId) {
        PurchaseOrder purchaseOrder = orderRepository.findByUniqueId(uniqueId);
        if (purchaseOrder == null) {
            throw new RuntimeException("Not find order");
        }
        String accessToken = oauthTokenService.getLastToken();
        HttpPost request = new HttpPost("https://api-m.sandbox.paypal.com/v2/checkout/orders/" + purchaseOrder.getPaypalId() + "/capture");
        request.setHeader("Authorization", "Bearer " + accessToken);
        StringEntity body = null;
        CloseableHttpResponse response = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            body = new StringEntity("{\"note_to_payer\":\"Thank you for your purchase!\",\"final_capture\":true}");
            body.setContentType("application/json");
            if (body == null) {
                throw new RuntimeException("Not work confirmPayment() ");
            }
            request.setEntity(body);
            response = httpClient.execute(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(response.getStatusLine().getStatusCode() >= 422){
            throw new RuntimeException("No many many many!!!!!");
        }
        if (response.getStatusLine().getStatusCode() >= 400 && response.getStatusLine().getStatusCode() < 500) {
            throw new RuntimeException("not captured payment");
        }
        if (response.getStatusLine().getStatusCode() >= 500) {
            throw new RuntimeException("Not work paypal server!");
        }

        purchaseOrder.setPaid(true);
        orderRepository.save(purchaseOrder);
        for (Reservation reservation : purchaseOrder.getBasket()) {
            reservation.setPaid(true);
            reservationRepository.save(reservation);
        }

        return "Thanks payment!";
    }
}