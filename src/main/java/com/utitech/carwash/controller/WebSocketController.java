package com.utitech.carwash.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    // Listen for messages from the client
    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public String sendMessage(String message) {
        return "Hello, " + message + "!";
    }
}
