package hu.progmasters.hotel.exception;

import lombok.Getter;

@Getter
public class NoImageException extends RuntimeException{

    private final String errorMessage;

    public NoImageException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}