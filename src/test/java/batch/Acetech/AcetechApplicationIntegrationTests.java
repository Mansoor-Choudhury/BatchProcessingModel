package batch.Acetech;

import batch.Acetech.model.Batch;
import batch.Acetech.repository.BatchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {KafkaAutoConfiguration.class})
@SpringJUnitConfig(classes = {AcetechApplication.class})
class AcetechApplicationIntegrationTests {

	private String BATCH_DATA_PATH = "src/test/resources/data_Batch.json";

	@MockBean
	private KafkaTemplate<String, Batch> kafkaTemplate;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BatchRepository batchRepository;

	@Test
	void integrationTestCreateBatchSuccessfully() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String jsonRequestBody = new String(Files.readAllBytes(Paths.get(BATCH_DATA_PATH)));

		when(batchRepository.save(any())).thenReturn(validBatchData());
		mockMvc.perform(MockMvcRequestBuilders.post("/batches/createBatch")
						.headers(headers)
						.content(jsonRequestBody))
				.andExpect(status().isOk());

	}

	private Batch validBatchData(){
		LocalDate localDate = LocalDate.parse("2024-02-22");
		return Batch.builder().batchid("abc").batchTypeId("abc123").batchTypeDescription("typeABC").
				batchExpirationDate(localDate).batchCount(10).build();
	}


}
