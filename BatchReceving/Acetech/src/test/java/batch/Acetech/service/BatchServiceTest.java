package batch.Acetech.service;

import batch.Acetech.model.Batch;
import batch.Acetech.model.BatchError;
import batch.Acetech.model.BatchResponse;
import batch.Acetech.repository.BatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class BatchServiceTest {

    @Mock
    private BatchRepository batchRepository;

    @InjectMocks
    private BatchService batchService;

    @Mock
    ApplicationEventPublisher applicationEventPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidBatchCreationSuccess() {
        // Given
        Batch batch1 = validBatchDataOne();
        Batch batch2 = validBatchDataTwo();

        List<Batch> batches = Arrays.asList(batch1, batch2);
        when(batchRepository.existsById("abc")).thenReturn(false);
        when(batchRepository.existsById("xyz")).thenReturn(false);

        // When
        BatchResponse batchResponse = batchService.createBatch(batches);

        // Then
        verify(batchRepository, times(2)).save(any(Batch.class));
        verify(applicationEventPublisher, times(2)).publishEvent(any(Batch.class));

        assertEquals(HttpStatus.OK.value(), batchResponse.getStatusCode());
        assertEquals("Batch Processing completed successfully", batchResponse.getMessage());
        assertEquals(2, batchResponse.getSuccessfulBatchList().size());
        assertTrue(batchResponse.getFailedBatchList().isEmpty());
    }

    @Test
    void testCreateBatchWithInvalidBatchId() {
        // Given
        Batch batch1 = inValidBatchData();
        List<Batch> batches = Arrays.asList(batch1);

        // When
        BatchResponse batchResponse = batchService.createBatch(batches);

        assertEquals(HttpStatus.OK.value(), batchResponse.getStatusCode());
        assertEquals("Batch Processing completed successfully", batchResponse.getMessage());
        assertEquals(0, batchResponse.getSuccessfulBatchList().size());
        assertEquals(1, batchResponse.getFailedBatchList().size());

        BatchError failedBatch = batchResponse.getFailedBatchList().get(0);
        assertEquals("", failedBatch.getBatchId());
        assertEquals("Batch ID is required", failedBatch.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, failedBatch.getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.value(), failedBatch.getStatusCode());
    }

    @Test
    void testCreateBatchWithExistingBatchId() {
        // Given
        Batch batch1 = validBatchDataOne();
        Batch batch2 = validBatchDataTwo();

        List<Batch> batches = Arrays.asList(batch1, batch2);

        when(batchRepository.existsById("abc")).thenReturn(true);
        when(batchRepository.existsById("xyz")).thenReturn(true);

        BatchResponse batchResponse = batchService.createBatch(batches);

        assertEquals(HttpStatus.OK.value(), batchResponse.getStatusCode());
        assertEquals("Batch Processing completed successfully", batchResponse.getMessage());
        assertEquals(0, batchResponse.getSuccessfulBatchList().size());
        assertEquals(2, batchResponse.getFailedBatchList().size());

        BatchError failedBatch = batchResponse.getFailedBatchList().get(0);
        assertEquals("abc", failedBatch.getBatchId());
        assertEquals("Batch ID already exists", failedBatch.getMessage());
        assertEquals(HttpStatus.CONFLICT, failedBatch.getStatus());
        assertEquals(HttpStatus.CONFLICT.value(), failedBatch.getStatusCode());
    }

    private Batch validBatchDataOne(){
        LocalDate localDate = LocalDate.parse("2024-02-22");
        return Batch.builder().batchid("abc").batchTypeId("abc123").batchTypeDescription("typeABC").
                batchExpirationDate(localDate).batchCount(10).build();
    }

    private Batch validBatchDataTwo(){
        LocalDate localDate = LocalDate.parse("2024-02-22");
        return Batch.builder().batchid("xyz").batchTypeId("abc123").batchTypeDescription("typeABC").
                batchExpirationDate(localDate).batchCount(10).build();
    }

    private Batch inValidBatchData(){
        LocalDate localDate = LocalDate.parse("2024-02-22");
        return Batch.builder().batchid("").batchTypeId("abc123").batchTypeDescription("typeABC").
                batchExpirationDate(localDate).batchCount(10).build();
    }
}
