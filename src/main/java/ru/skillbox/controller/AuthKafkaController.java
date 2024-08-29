package ru.skillbox.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.dto.User;
import ru.skillbox.dto.kafka.KafkaAuthEvent;

import java.security.SecureRandom;
import java.util.Random;

//@RestController
//@RequestMapping("/api/users")
@RequiredArgsConstructor
@Component
public class AuthKafkaController {

    private final KafkaTemplate<String, KafkaAuthEvent> kafkaTemplate;
    private final String topicName = "auth-topic";

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int STRING_LENGTH = 10;
    private static final SecureRandom random = new SecureRandom();


//    @PostMapping
//    @Scheduled(fixedRate = 3000)
//    public ResponseEntity<String> sendUser(@RequestBody User user) {
//        kafkaTemplate.send(topicName, user);
//        return ResponseEntity.ok("User sent to Kafka");
//    }

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
