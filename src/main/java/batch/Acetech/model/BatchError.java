package batch.Acetech.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class BatchError {
    private String batchId;
    private String message;
    private int statusCode;
    private HttpStatus status;
}
