package hu.progmasters.hotel.exception;

import lombok.Getter;

@Getter
public class FileSizeException extends RuntimeException {

    private final String errorMessage;

    public FileSizeException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}