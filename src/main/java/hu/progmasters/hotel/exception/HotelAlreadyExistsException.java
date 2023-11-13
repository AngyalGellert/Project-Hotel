package hu.progmasters.hotel.exception;

public class HotelAlreadyExistsException extends RuntimeException {

    public HotelAlreadyExistsException(String hotelName) {
        super("Hotel named " + hotelName + " already exists");
    }

}
