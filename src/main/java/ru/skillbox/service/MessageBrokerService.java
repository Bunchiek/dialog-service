package ru.skillbox.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ru.skillbox.configuration.RabbitMQConfig;
import ru.skillbox.dto.MessageWebSocketDto;
import ru.skillbox.dto.MessageWebSocketRs;
import ru.skillbox.entity.Dialog;
import ru.skillbox.repository.DialogRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageBrokerService {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageConsumerService messageConsumerService;
    private final DialogRepository dialogRepository;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(MessageWebSocketDto messageWebSocketDTO) {
        try {
            // Поиск текущего диалога
            Dialog currentDialog = dialogRepository
                    .findByParticipants(
                            messageWebSocketDTO.getData().getConversationPartner1(),
                            messageWebSocketDTO.getData().getConversationPartner2())
                    .orElseThrow(() -> new EntityNotFoundException("Диалог не найден"));

            // Устанавливаем ID диалога в сообщение
            MessageWebSocketRs rs = messageWebSocketDTO.getData();
            rs.setId(currentDialog.getId());
            messageWebSocketDTO.setData(rs);

            // Сохраняем сообщение
            messageConsumerService.saveMessage(messageWebSocketDTO);
            log.info("Message saved successfully.");

            // Отправка сообщения клиентам через WebSocket
            String topic = "/topic/dialog/" + currentDialog.getId();
            log.info("Formatted topic path: '{}'", topic);
            messagingTemplate.convertAndSend(topic, messageWebSocketDTO);
            log.info("Message sent to WebSocket topic: {}", topic);

        } catch (EntityNotFoundException e) {
            log.error("Error finding dialog: {}", e.getMessage());
        } catch (Exception e) {
            log.error("An error occurred: {}", e.getMessage(), e);
        }
    }
}
