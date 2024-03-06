package com.example.demo.handler;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class TestChatWebSocketHandler extends ChatWebSocketHandler {
    // handleTextMessage 메서드를 public으로 노출
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
    }
}