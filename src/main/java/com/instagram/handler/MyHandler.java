package com.instagram.handler;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyHandler extends TextWebSocketHandler {


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        session.sendMessage(new TextMessage("соединение установлено"));

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        session.sendMessage(new TextMessage("pong"));
    }
}
