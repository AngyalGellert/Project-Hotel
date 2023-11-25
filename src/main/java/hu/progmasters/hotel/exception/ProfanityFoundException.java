package hu.progmasters.hotel.exception;

public class ProfanityFoundException extends RuntimeException {

    public ProfanityFoundException() {
        super("A profanity was found in this description, it cannot be saved because of this.");
    }
}
