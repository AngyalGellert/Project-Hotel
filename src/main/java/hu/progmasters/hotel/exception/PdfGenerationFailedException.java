package hu.progmasters.hotel.exception;

public class PdfGenerationFailedException extends RuntimeException {
    public PdfGenerationFailedException(String message) {
        super(message);
    }
}
