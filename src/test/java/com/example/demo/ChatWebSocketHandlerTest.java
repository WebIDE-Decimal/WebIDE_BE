package com.example.demo;


import com.example.demo.handler.ChatWebSocketHandler;
import com.example.demo.handler.TestChatWebSocketHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.Mockito.*;


@SpringBootTest
public class ChatWebSocketHandlerTest {

    private TestChatWebSocketHandler chatWebSocketHandler;

    @BeforeEach
    public void setUp() {
        chatWebSocketHandler = new TestChatWebSocketHandler();
    }

    @Test
    public void whenMessageReceived_thenSendMessageToAllSessions() throws Exception {
        // 세션 모킹
        WebSocketSession session1 = mock(WebSocketSession.class);
        WebSocketSession session2 = mock(WebSocketSession.class);
        when(session1.isOpen()).thenReturn(true);
        when(session2.isOpen()).thenReturn(true);

        // 세션 추가
        Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
        sessions.put("1", session1);
        sessions.put("2", session2);
        ReflectionTestUtils.setField(chatWebSocketHandler, "sessions", sessions);

        TextMessage message = new TextMessage("test message");

        // 메시지 처리
        chatWebSocketHandler.handleTextMessage(session1, message);

        // 세션별 메시지 전송 확인
        verify(session1, times(1)).sendMessage(message);
        verify(session2, times(1)).sendMessage(message);
    }
}