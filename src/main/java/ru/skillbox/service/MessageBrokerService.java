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

        // Логируем получение сообщения
        log.info("Received message: {}", messageWebSocketDTO);

        try {
            // Поиск текущего диалога
            Dialog currentDialog = dialogRepository
                    .findByParticipants(messageWebSocketDTO.getData().getConversationPartner1(), messageWebSocketDTO.getData().getConversationPartner2())
                    .orElseThrow(() -> new EntityNotFoundException("Диалог не найден"));

            // Логируем найденный диалог
            log.info("Found dialog with ID: {}", currentDialog.getId());

            // Устанавливаем ID диалога в ответе
            MessageWebSocketRs rs = messageWebSocketDTO.getData();
            rs.setId(currentDialog.getId());
            messageWebSocketDTO.setData(rs);

            // Отправляем сообщение обратно клиентам через WebSocket
            log.info("Sending message to WebSocket: {}", messageWebSocketDTO);
            messagingTemplate.convertAndSend("/topic/public", messageWebSocketDTO);

            // Сохраняем сообщение
            messageConsumerService.saveMessage(messageWebSocketDTO);
            log.info("Message saved successfully.");

        } catch (EntityNotFoundException e) {
            // Логируем ошибку, если диалог не найден
            log.error("Error finding dialog: {}", e.getMessage());
        } catch (Exception e) {
            // Логируем другие возможные ошибки
            log.error("An error occurred: {}", e.getMessage(), e);
        }
    }
}
