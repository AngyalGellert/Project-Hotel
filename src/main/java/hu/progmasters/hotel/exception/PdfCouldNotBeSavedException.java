package hu.progmasters.hotel.exception;

public class PdfCouldNotBeSavedException extends  RuntimeException {
    public PdfCouldNotBeSavedException(String message) {
        super(message);
    }
}
