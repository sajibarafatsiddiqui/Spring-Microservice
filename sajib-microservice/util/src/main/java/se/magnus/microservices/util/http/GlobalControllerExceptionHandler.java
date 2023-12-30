package se.magnus.microservices.util.http;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import se.magnus.microservices.api.exception.InvalidInputException;
import se.magnus.microservices.api.exception.NotFoundException;


@RestControllerAdvice
public class GlobalControllerExceptionHandler {

private final Logger log = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

@ResponseStatus(NOT_FOUND)
@ExceptionHandler(NotFoundException.class)
public @ResponseBody HttpErrorInfo handleNotFounddException(ServerHttpRequest req, NotFoundException ex){
    return createHttpErrorInfo(ex,req,NOT_FOUND);
}
@ResponseStatus(UNPROCESSABLE_ENTITY)
@ExceptionHandler(InvalidInputException.class)
public @ResponseBody HttpErrorInfo handleNotFounddException(ServerHttpRequest req, InvalidInputException ex){
    return createHttpErrorInfo(ex,req,NOT_FOUND);
}

    private HttpErrorInfo createHttpErrorInfo(Exception ex,ServerHttpRequest request,HttpStatus httpStatus){
       final String path = request.getPath().pathWithinApplication().value();
       final String message=ex.getMessage();
       log.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);
       return new HttpErrorInfo(message,path,httpStatus)  ;                                                                                                       

    }
}
