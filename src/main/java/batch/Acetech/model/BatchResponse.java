package batch.Acetech.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class BatchResponse {
    private List<Batch> successfulBatchList;
    private List<BatchError> failedBatchList;
    private int statusCode;
    private String message;
}
