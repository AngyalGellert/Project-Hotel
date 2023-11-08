package hu.progmasters.hotel.exception;

public class HotelHasNoRoomsException extends RuntimeException {

    public HotelHasNoRoomsException(Long hotelId) {
        super("Hotel with ID " + hotelId + " has no available rooms");
    }

}
