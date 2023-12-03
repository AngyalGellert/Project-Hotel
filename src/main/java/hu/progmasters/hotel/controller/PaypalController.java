package hu.progmasters.hotel.controller;

import hu.progmasters.hotel.dto.request.CreateOrderRequest;
import hu.progmasters.hotel.dto.response.ResponseToken;
import hu.progmasters.hotel.repository.UserRepository;
import hu.progmasters.hotel.service.OauthTokenService;
import hu.progmasters.hotel.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/paypal")
public class PaypalController {
    private final UserRepository userRepository;

    private final OauthTokenService oauthTokenService;

    private final OrderService orderService;

    @Autowired
    public PaypalController(OauthTokenService oauthTokenService, OrderService orderService,
                            UserRepository userRepository) {
        this.oauthTokenService = oauthTokenService;
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @PostMapping("/token")
    public ResponseEntity<ResponseToken> token() {
        log.info("Http request, Get paypal token");
        return new ResponseEntity<>( oauthTokenService.requestToken(), HttpStatus.OK);
    }

    @PostMapping("/payment/{id}")
    public ResponseEntity<String> payment(@PathVariable("id") Long id) {
        log.info("Payment by Order id:  " + id);
        return new ResponseEntity<>(orderService.paymentOrder(id), HttpStatus.OK);
    }

    @PostMapping("/createorder")
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest request) {
        log.info("Create Order ");
        orderService.createOrder(request.getUserId(), request.getReservationsId());
        return new ResponseEntity<>("Done order.", HttpStatus.OK);
    }
    @PostMapping("/payment/confirm/{id}")
    public ResponseEntity<String> createOrder(@PathVariable("id") String uniqueId) {
        log.info("Done payment in paypal this uniqueID: " + uniqueId);
        return new ResponseEntity<>("Confirmed payment." + orderService.confirmPayment(uniqueId), HttpStatus.OK);
    }

}
