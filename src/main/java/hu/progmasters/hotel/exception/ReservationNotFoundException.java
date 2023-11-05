package hu.progmasters.hotel.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException() {
        super("This reservation does not exist");
    }
}
