package ru.skillbox.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.skillbox.dto.kafka.KafkaNewAccountEvent;


@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaAuthEventListener {


    @KafkaListener(topics = "${app.kafka.kafkaNewAccountTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "kafkaAuthEventConcurrentKafkaListenerContainerFactory")
    public void listenEventAuth(@Payload KafkaNewAccountEvent kafkaNewAccountEvent) {
    //TODO реализовать логику сохранения пользователя
        log.info("Создан новый аккаунт! UUID:{}, имя пользователя: {}, фамилия {}, email {}",
                kafkaNewAccountEvent.getId(), kafkaNewAccountEvent.getFirstName(), kafkaNewAccountEvent.getLastName(), kafkaNewAccountEvent.getEmail());

    }

}
