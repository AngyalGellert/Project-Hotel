package hu.progmasters.hotel.exception;

public class RoomAlreadyDeletedException extends RuntimeException{

    public RoomAlreadyDeletedException(Long roomId) {
        super("Room with ID " + roomId + " is already deleted");
    }
}
