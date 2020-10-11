package com.instagram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instagram.dao.DaoUser;
import com.instagram.dto.DtoChat;
import com.instagram.model.Message;
import com.instagram.service.ServiceChat;
import com.instagram.service.ServiceMessageHistory;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ControllerChat {

    @Autowired
    private DaoUser daoUser;

    @Autowired
    private SimpMessagingTemplate msgTemplate;

    @Autowired
    private ServiceChat serviceChat;

    @Autowired
    private ServiceMessageHistory serviceMessageHistory;

    @Autowired
    private ObjectMapper mapper;

    @SneakyThrows
    @MessageMapping("/stompDirect.getChats")
    @SendTo("/topic/stompDirect/{username}")
    public void getChats(Authentication auth) {
        List<DtoChat> chats = serviceChat.sendChatsToUser(auth.getName());
        String chatsInJson = mapper.writeValueAsString(chats);
        String path = "/topic/stompDirect/"+auth.getName();
        msgTemplate.convertAndSend(path, chatsInJson);
    }

    @MessageMapping("/stompChat.sendMessage/{chatId}")
    @SendTo("/topic/stompChat/{chatId}")
    @SneakyThrows
    public @ResponseBody Message messageTransmitter(@Payload String msgJSON, @DestinationVariable Long chatId, Authentication auth) {
        Long iamId = daoUser.getUserByUsername(auth.getName()).getId();
        Message msg = mapper.readValue(msgJSON,Message.class);
        msg.setChatId(chatId);
        msg.setOwnerId(iamId);

        serviceMessageHistory.saveMessage(msg);
        
        return msg;
    }

}
