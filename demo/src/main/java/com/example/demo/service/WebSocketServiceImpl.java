package com.example.demo.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketServiceImpl implements WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendMessage(String message) {
        messagingTemplate.convertAndSend("/topic/customer/messages", message);
    }
}