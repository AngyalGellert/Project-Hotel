package hu.progmasters.hotel.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(Long message) {
        super("This reservation does not exist");
    }
}