package hu.progmasters.hotel.exception;

public class GeocodingException extends RuntimeException{

    public GeocodingException() { super("An error occurred with GeocodingApi");
    }
}
