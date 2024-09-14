package ru.skillbox.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.skillbox.dto.kafka.KafkaNewAccountEvent;
import ru.skillbox.entity.Account;
import ru.skillbox.repository.AccountRepository;


@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaAuthEventListener {

    private final AccountRepository accountRepository;

    @KafkaListener(topics = "${app.kafka.kafkaNewAccountTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "kafkaAuthEventConcurrentKafkaListenerContainerFactory")
    public void listenEventAuth(@Payload KafkaNewAccountEvent kafkaNewAccountEvent) {
        Account newAccount = Account.builder()
                .id(kafkaNewAccountEvent.getId())
                .firstName(kafkaNewAccountEvent.getFirstName())
                .lastName(kafkaNewAccountEvent.getLastName()).build();
        accountRepository.save(newAccount);
        log.info("Создан новый аккаунт! UUID:{}, имя пользователя: {}, фамилия {}, email {}",
                kafkaNewAccountEvent.getId(), kafkaNewAccountEvent.getFirstName(), kafkaNewAccountEvent.getLastName(), kafkaNewAccountEvent.getEmail());

    }

}
