package partners.service;

import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BatchPartnerConsumer {

    private final Logger log;

    public BatchPartnerConsumer(Logger logger) {
        this.log = logger;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message){
        log.info(String.format("Batch received from AceTech-> %s", message));
    }
}
