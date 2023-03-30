package batch.Acetech.controller;

import batch.Acetech.model.Batch;
import batch.Acetech.model.BatchResponse;
import batch.Acetech.service.BatchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = BatchController.class)
@ContextConfiguration(classes = {BatchController.class})
@EnableSpringDataWebSupport
public class BatchControllerTests {

    private String BATCH_DATA_PATH = "src/test/resources/data_Batch.json";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BatchService batchService;

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testBatchReceivedSuccessfully() throws Exception {
        String jsonRequestBody = new String(Files.readAllBytes(Paths.get(BATCH_DATA_PATH)));

        Mockito.when(batchService.createBatch(Mockito.any())).thenReturn(validBatchResponse());
        mockMvc.perform(MockMvcRequestBuilders.post("/batches/createBatch")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isOk());
    }


    private BatchResponse validBatchResponse(){
        LocalDate localDate = LocalDate.ofEpochDay(2024-02-22);
        Batch batch = Batch.builder().batchid("abc").batchTypeId("abc123").batchTypeDescription("type").
                batchExpirationDate(localDate).batchCount(10).build();
        List<Batch> batchList = new ArrayList<>();
        batchList.add(batch);
        return BatchResponse.builder().successfulBatchList(batchList).message("Created Successfully").statusCode(200).build();
    }

}
