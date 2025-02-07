package com.utitech.carwash.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utitech.carwash.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class SocketHandler extends TextWebSocketHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket connection established: " + session.getId());
        // Optionally, send a welcome message.
        session.sendMessage(new TextMessage("Welcome to the plain WebSocket connection!"));
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Called when a text message is received from the client.
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);

        // For example, prepare a JSON response.
        // (You can create your own response object if needed)
        Message response = new Message(1, "DONE", ZonedDateTime.now());
        String jsonResponse = mapper.writeValueAsString(response);

        // Send the JSON response back to the client.
        session.sendMessage(new TextMessage(jsonResponse));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Called after the WebSocket connection is closed.
        System.out.println("WebSocket connection closed: " + session.getId());
    }
}
