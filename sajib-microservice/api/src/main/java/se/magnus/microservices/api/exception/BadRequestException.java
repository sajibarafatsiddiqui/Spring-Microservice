package se.magnus.microservices.api.exception;
import java.util.Objects;

public class BadRequestException extends RuntimeException {
 
    String message;
    Throwable cause;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message,cause);
    }

  

    
}
