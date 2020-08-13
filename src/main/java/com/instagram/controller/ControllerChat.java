package com.instagram.controller;

import com.instagram.dto.DtoChat;
import com.instagram.service.ServiceChat;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ControllerChat {

    @Autowired
    private ServiceChat serviceChat;

    @MessageMapping("/stompDirect.getChats/{username}")
    @SendTo("/topic/stompDirect/{username}")
    @SneakyThrows
    public List<DtoChat> chatsGetter(Authentication auth, @DestinationVariable String username) {
        return serviceChat.getDtoChatsByUsername(username);
    }

    @MessageMapping("/stompChat.sendMessage/{username}")
    @SneakyThrows
    public void messageTransmitter(@Payload String msgJSON, @DestinationVariable("username") String opponentUsername) {
        serviceChat.sendMessage(msgJSON, opponentUsername);
    }

}
