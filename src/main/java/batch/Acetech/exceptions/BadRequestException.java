package batch.Acetech.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {

    public BadRequestException(String errorMessage) {
        super(HttpStatus.BAD_REQUEST, errorMessage);
    }
}
