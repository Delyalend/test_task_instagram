package com.instagram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instagram.dao.DaoChat;
import com.instagram.dao.DaoUser;
import com.instagram.dto.DtoChat;
import com.instagram.model.Chat;
import com.instagram.model.User;
import com.instagram.service.ServiceChat;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControllerDirect {
    @Autowired
    private DaoChat daoChat;

    @Autowired
    private DaoUser daoUser;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;


    @Autowired
    private ServiceChat serviceChat;
    
    @MessageMapping("/stompDirect.getChats/{username}")
    @SendTo("/topic/stompDirect/{username}")
    @SneakyThrows
    public List<DtoChat> getChats(Authentication authentication, @DestinationVariable("username") String username) {
        User iam = daoUser.findUserWithoutRoleByUsername(authentication.getName());

        List<DtoChat> dtoChats = serviceChat.getDtoChatsByUsername(username);

        messagingTemplate.convertAndSend("/stompDirect/"+authentication.getName(), dtoChats);

        return dtoChats;
    }


    @GetMapping("/direct")
    public String getDirect(Authentication authentication, Model model) {
        User iam = daoUser.findUserWithoutRoleByUsername(authentication.getName());
        model.addAttribute("iam", iam);
        return "direct";
    }



}
