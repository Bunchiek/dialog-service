package ru.skillbox.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig  implements WebSocketMessageBrokerConfigurer{

    @Value("${app.rabbitMQ.host}")
    private String rabbitMqHost;

    @Value("${app.rabbitMQ.port}")
    private Integer rabbitMqPort;

    @Value("${app.rabbitMQ.login}")
    private String rabbitMqLogin;

    @Value("${app.rabbitMQ.password}")
    private String rabbitMqPassword;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue")
//                .setRelayHost(rabbitMqHost)
//                .setRelayPort(rabbitMqPort)
//                .setClientLogin(rabbitMqLogin)
//                .setClientPasscode(rabbitMqPassword)
        ;
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/v1/streaming/ws")
                .setAllowedOriginPatterns("*");
    }

}


