package hu.progmasters.hotel.exception;

public class RoomNotFoundException extends RuntimeException{

    public RoomNotFoundException(Long roomId) {
        super("Room with ID " + roomId + " not found");
    }

}
