package ru.skillbox.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.skillbox.dto.kafka.KafkaAuthEvent;

import java.security.SecureRandom;


@RequiredArgsConstructor
@Component
public class AuthKafkaController {

    private final KafkaTemplate<String, KafkaAuthEvent> kafkaTemplate;
    private final String topicName = "auth-topic";
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int STRING_LENGTH = 10;
    private static final SecureRandom random = new SecureRandom();


    @Scheduled(fixedRate = 10000)
    public void generateAuth() {
        KafkaAuthEvent kafkaAuthEvent = new KafkaAuthEvent();
        kafkaAuthEvent.setEmail(generateRandomString(15));
        kafkaAuthEvent.setFirstName(generateRandomString(5));
        kafkaAuthEvent.setLastName(generateRandomString(5));
        kafkaTemplate.send(topicName, kafkaAuthEvent);

    }


    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }




}
