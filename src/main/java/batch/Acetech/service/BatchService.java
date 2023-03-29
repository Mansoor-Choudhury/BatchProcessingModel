package batch.Acetech.service;

import batch.Acetech.exceptions.BadRequestException;
import batch.Acetech.exceptions.ResourceAlreadyExistsException;
import batch.Acetech.model.Batch;
import batch.Acetech.model.BatchError;
import batch.Acetech.model.BatchResponse;
import batch.Acetech.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BatchService {

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @Transactional
    public BatchResponse createBatch(List<Batch> batches){
        List<Batch> successfulBatchList = new ArrayList<>();
        List<BatchError> failedBatchList = new ArrayList<>();

        for (Batch batch : batches) {
            try {
                checkBatchId(batch.getBatchid());
                batchRepository.save(batch);
                successfulBatchList.add(batch);
            }catch (Exception e){
                if(e instanceof BadRequestException){
                    BatchError batchError = BatchError.builder().batchId(batch.getBatchid()).
                            message(((BadRequestException) e).getErrorMessage()).
                            status(((BadRequestException) e).getErrorCode()).
                            statusCode(((BadRequestException) e).getErrorCode().value()).build();
                    failedBatchList.add(batchError);
                }else if(e instanceof ResourceAlreadyExistsException){
                    BatchError batchError = BatchError.builder().batchId(batch.getBatchid()).
                            message(((ResourceAlreadyExistsException) e).getErrorMessage()).
                            status(((ResourceAlreadyExistsException) e).getErrorCode()).
                            statusCode(((ResourceAlreadyExistsException) e).getErrorCode().value()).build();
                    failedBatchList.add(batchError);
                }
            }
        }

        for (Batch batch : successfulBatchList) {
            applicationEventPublisher.publishEvent(batch);
        }

        return BatchResponse.builder().successfulBatchList(successfulBatchList).
                failedBatchList(failedBatchList).
                statusCode(HttpStatus.OK.value()).
                message("Batch Processing completed successfully").build();
    }


    private void checkBatchId(String batchId){
        if (batchId.trim().isEmpty()) {
            throw new BadRequestException("Batch ID is required");
        }
        if (batchRepository.existsById(batchId)) {
            throw new ResourceAlreadyExistsException("Batch ID already exists");
        }
    }
}
