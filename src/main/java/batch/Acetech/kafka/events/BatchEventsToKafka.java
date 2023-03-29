package batch.Acetech.kafka.events;

import batch.Acetech.model.Batch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class BatchEventsToKafka {


    @Value("${spring.kafka.topic.name}")
    private String batchTopicName;

    private static final Logger log = LoggerFactory.getLogger(BatchEventsToKafka.class);

    @Autowired
    private KafkaTemplate<String, Batch> kafkaTemplate;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @EventListener
    @TransactionalEventListener
    public void sendBatchEventsToPartners(Batch batch){
            Message<Batch> message = MessageBuilder
                    .withPayload(batch)
                    .setHeader(KafkaHeaders.TOPIC, batchTopicName)
                    .build();
            kafkaTemplate.send(message);
        log.info(String.format("Batch Event has been sent to topic %s with data %s"), batchTopicName, batch.toString());
    }
}
