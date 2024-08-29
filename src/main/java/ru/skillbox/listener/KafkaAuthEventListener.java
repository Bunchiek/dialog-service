package ru.skillbox.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.skillbox.dto.kafka.KafkaAuthEvent;
import ru.skillbox.dto.kafka.KafkaNewAccountEvent;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaAuthEventListener {


    @KafkaListener(topics = "${app.kafka.kafkaNewAccountTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "kafkaAuthEventConcurrentKafkaListenerContainerFactory")
    public void listenEventAuth(@Payload KafkaNewAccountEvent kafkaAuthEvent,
                                   @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                                   @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                   @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                                   @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {

        log.info("Создан новый аккаунт! UUID:{}, имя пользователя: {}, фамилия {}, email {}",
                kafkaAuthEvent.getId(), kafkaAuthEvent.getFirstName(), kafkaAuthEvent.getLastName(), kafkaAuthEvent.getEmail());

    }

}
