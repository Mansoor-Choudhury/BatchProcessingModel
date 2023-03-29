package batch.Acetech.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
public class ResourceAlreadyExistsExceptionTest {

    @Test
    public void testBadRequestException() {
        String errorMessage = "Conflict";
        ResourceAlreadyExistsException exception = new ResourceAlreadyExistsException(errorMessage);

        assertEquals(HttpStatus.CONFLICT, exception.getErrorCode());
        assertEquals(errorMessage, exception.getMessage());
    }
}
