package se.magnus.microservices.api.exception;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException() {
        
    }
    public InvalidInputException(String message,Throwable cause) {
        super(message,cause); 
    }
    public InvalidInputException(String message) {
        super(message);
    }
    public InvalidInputException(Throwable cause) {
        super(cause);
    }
    
}
