package hu.progmasters.hotel.exception;


public class EmailAlreadyInUseException extends RuntimeException {

    public EmailAlreadyInUseException(String email) {
        super("This " + email + " already in use");

    }
}
