package batch.Acetech.exceptions;


import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {

    private final HttpStatus errorCode;
    private final String errorMessage;

    public BaseException(HttpStatus errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
