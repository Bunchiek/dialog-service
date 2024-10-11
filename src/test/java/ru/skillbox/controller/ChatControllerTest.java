//package ru.skillbox.controller;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.logging.log4j.LogManager;
//import org.junit.After;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.messaging.converter.MappingJackson2MessageConverter;
//import org.springframework.messaging.simp.stomp.*;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.web.socket.client.WebSocketClient;
//import org.springframework.web.socket.client.standard.StandardWebSocketClient;
//import org.springframework.web.socket.messaging.WebSocketStompClient;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.TimeoutException;
//import java.util.logging.Logger;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Slf4j
//public class ChatControllerTest {
//
//
//    private List<StompSession> sessions = new ArrayList<>();
//    private String test;
//    private CompletableFuture<String> answer;
//    private WebSocketStompClient stompClient;
//    private StompSessionHandler sessionHandler = new MyStompSessionHandler();
//
//
//    @BeforeEach
//    public void setup() {
//        test = "test";
//        WebSocketClient client = new StandardWebSocketClient();
//        stompClient = new WebSocketStompClient(client);
//        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//    }
//
//    @AfterEach
//    public void cleanUp() {
//        this.sessions.forEach(StompSession::disconnect);
//        this.sessions.clear();
//    }
//
//    @Test
//    public void shouldSendAndReceiveMessage() throws Exception {
//        System.out.println(test);
//        CompletableFuture<String> answer = new CompletableFuture<>();
////        var stompSession =
//                connectAndSubscribe(answer);
////        stompSession.send("/app/echo", "Hello World!".getBytes());
//        var result = answer.get(1, TimeUnit.SECONDS);
//        assertThat(result).isEqualTo("RECEIVED: Hello World!");
//    }
//
//    private void connectAndSubscribe(CompletableFuture<String> answer)
//            throws InterruptedException, ExecutionException, TimeoutException {
//        var uri = "ws://localhost:8787/api/v1/streaming/ws";
//        stompClient.connect(uri, sessionHandler);
////        stompClient.
//
//    }
//
//    private static class MyStompSessionHandler  extends StompSessionHandlerAdapter  {
//
//        @Override
//        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
//            log.info("New session established : " + session.getSessionId());
//            session.subscribe("/topic/messages", this);
//            log.info("Subscribed to /topic/messages");
//            session.send("/app/chat", getSampleMessage());
//            log.info("Message sent to websocket server");
//        }
//
//        @Override
//        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
//            log.error("Got an exception", exception);
//        }
//
//        @Override
//        public Type getPayloadType(StompHeaders headers) {
//            return Message.class;
//        }
//
//        @Override
//        public void handleFrame(StompHeaders headers, Object payload) {
//            Message msg = (Message) payload;
//            log.info("Received : " + msg.getText() + " from : " + msg.getFrom());
//        }
//
//        /**
//         * A sample message instance.
//         * @return instance of <code>Message</code>
//         */
//        private Message getSampleMessage() {
//            Message msg = new Message();
//            msg.setFrom("Nicky");
//            msg.setText("Howdy!!");
//            return msg;
//        }
//    }
//
//    private static class Message {
//
//        private String from;
//        private String text;
//
//        public String getText() {
//            return text;
//        }
//
//        public String getFrom() {
//            return from;
//        }
//
//        public void setFrom(String from) {
//            this.from = from;
//        }
//
//        public void setText(String text) {
//            this.text = text;
//        }
//    }
//
//}