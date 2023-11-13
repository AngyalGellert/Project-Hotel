package hu.progmasters.hotel.exception;

import java.time.LocalDate;

public class ReservationAlreadyExistsException extends RuntimeException{

    public ReservationAlreadyExistsException(String guestName, LocalDate startDate, LocalDate endDate) {
        super("A reservation for " + guestName + " already exists for these dates: " + startDate + " to " + endDate);
    }
}
