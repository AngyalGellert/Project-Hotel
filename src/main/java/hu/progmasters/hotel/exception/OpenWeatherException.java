package hu.progmasters.hotel.exception;

public class OpenWeatherException extends RuntimeException{
    public OpenWeatherException() {
        super("An error occurred with OpenWeatherApi");
    }
}
