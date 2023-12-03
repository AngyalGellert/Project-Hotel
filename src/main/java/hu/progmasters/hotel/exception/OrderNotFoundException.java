package hu.progmasters.hotel.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long orderId) {
            super("Room with ID " + orderId + " not found");
        }

    }

