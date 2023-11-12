package hu.progmasters.hotel.exception;

public class ReservationAlreadyDeletedException extends RuntimeException{

    public ReservationAlreadyDeletedException(Long reservationId) {
        super("Reservation with ID " + reservationId + " was already deleted");
    }
}
