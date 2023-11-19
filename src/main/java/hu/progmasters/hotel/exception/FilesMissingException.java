package hu.progmasters.hotel.exception;

import lombok.Getter;

@Getter
public class FilesMissingException extends RuntimeException{

    private final String errorMessage;

    public FilesMissingException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}