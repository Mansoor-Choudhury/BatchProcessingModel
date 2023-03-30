package batch.Acetech.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
public class BadRequestExceptionTest {

    @Test
    public void testBadRequestException() {
        String errorMessage = "Bad request";
        BadRequestException exception = new BadRequestException(errorMessage);

        assertEquals(HttpStatus.BAD_REQUEST, exception.getErrorCode());
        assertEquals(errorMessage, exception.getMessage());
    }
}
