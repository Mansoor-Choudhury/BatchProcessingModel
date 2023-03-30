package batch.Acetech.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends BaseException {

    public ResourceAlreadyExistsException(String errorMessage) {
        super(HttpStatus.CONFLICT, errorMessage);
    }
}
