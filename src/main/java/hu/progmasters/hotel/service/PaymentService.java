package hu.progmasters.hotel.service;

import hu.progmasters.hotel.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private OrderService orderService;


    public PaymentService(OrderService orderService) {
        this.orderService = orderService;
    }



}
