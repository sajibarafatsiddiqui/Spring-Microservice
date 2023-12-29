package se.magnus.microservices.util.http;
import java.time.ZonedDateTime;
import java.util.Objects;

import org.springframework.http.HttpStatus;

public class HttpErrorInfo {
    private final ZonedDateTime timestamp;
    private final String message;
    private final String path;
    private final HttpStatus httpStatus; 

    public HttpErrorInfo() {
       timestamp=null;
       message=null;
       path=null;
       httpStatus=null;
       

    }

    public HttpErrorInfo(String message, String path, HttpStatus httpStatus) {
        this.timestamp = ZonedDateTime.now();
        this.message = message;
        this.path = path;
        this.httpStatus = httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }


    public String getMessage() {
        return this.message;
    }


    public String getPath() {
        return this.path;
    }


    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof HttpErrorInfo)) {
            return false;
        }
        HttpErrorInfo httpErrorInfo = (HttpErrorInfo) o;
        return Objects.equals(timestamp, httpErrorInfo.timestamp) && Objects.equals(message, httpErrorInfo.message) && Objects.equals(path, httpErrorInfo.path) && Objects.equals(httpStatus, httpErrorInfo.httpStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, message, path, httpStatus);
    }

    @Override
    public String toString() {
        return "{" +
            " timestamp='" + getTimestamp() + "'" +
            ", message='" + getMessage() + "'" +
            ", path='" + getPath() + "'" +
            ", httpStatus='" + getHttpStatus() + "'" +
            "}";
    }
}
