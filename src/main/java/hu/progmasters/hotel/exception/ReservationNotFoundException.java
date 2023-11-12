package hu.progmasters.hotel.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(Long reservationId) {
        super("Reservation with id: " + reservationId + " not found");
    }
}
