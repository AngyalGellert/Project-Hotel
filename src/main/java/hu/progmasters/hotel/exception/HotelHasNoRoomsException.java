package hu.progmasters.hotel.exception;

public class HotelHasNoRoomsException extends RuntimeException {

    public HotelHasNoRoomsException(Long hotelId) {
        super("Hotel with id " + hotelId + " has no available rooms");
    }

}
