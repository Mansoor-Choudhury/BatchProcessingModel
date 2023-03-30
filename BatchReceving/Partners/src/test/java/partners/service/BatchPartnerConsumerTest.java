package partners.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class BatchPartnerConsumerTest {
    @Mock
    private Logger logger;

    @InjectMocks
    private BatchPartnerConsumer batchPartnerConsumer;

    @Test
    public void testConsume() {
        String message = "Test message";
        batchPartnerConsumer.consume(message);
        verify(logger).info("Batch received from AceTech-> " + message);
    }
}

