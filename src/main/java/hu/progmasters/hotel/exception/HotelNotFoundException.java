package hu.progmasters.hotel.exception;

public class HotelNotFoundException extends RuntimeException {
    public HotelNotFoundException(Long hotelId) {
        super("Hotel with ID " + hotelId + " not found");
    }
}
