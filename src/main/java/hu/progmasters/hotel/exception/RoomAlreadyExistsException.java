package hu.progmasters.hotel.exception;

public class RoomAlreadyExistsException extends RuntimeException {

    public RoomAlreadyExistsException(String roomName) {
        super("Room named " + roomName + " already exists");
    }
}
