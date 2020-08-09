package com.instagram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instagram.dao.DaoChat;
import com.instagram.dao.DaoUser;
import com.instagram.model.Chat;
import com.instagram.model.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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


    @MessageMapping("/stompDirect.getChats")
    @SendTo("/topic/stompDirect")
    @SneakyThrows
    public List<Chat> getChats(Authentication authentication, SimpMessageHeaderAccessor headerAccessor) {
        //headerAccessor.getSessionAttributes().put("username", authentication.getName());
        //User user = objectMapper.readValue(json, User.class);
        User iam = daoUser.findUserWithoutRoleByUsername(authentication.getName());

        List<Chat> chatsByUserId = daoChat.getChatsByUserId(iam.getId());
        messagingTemplate.convertAndSend("/stompDirect", chatsByUserId);
        return chatsByUserId;
    }


    @GetMapping("/direct")
    public String getDirect(Authentication authentication, Model model) {

        User iam = daoUser.findUserWithoutRoleByUsername(authentication.getName());
        model.addAttribute("iam", iam);

  //      List<Chat> chats = daoChat.getChatsByUserId(iam.getId());
    //    model.addAttribute("chats", chats);

        return "direct";
    }


//    @GetMapping("/direct/{userId}")
//    public String getChatWithUserFromDirect(Authentication authentication, Model model, @PathVariable Long userId) {
//        User iam = daoUser.findUserWithoutRoleByUsername(authentication.getName());
//
//        Chat chat = daoChat.getChat(Long userId1, Long userId2)
//
//
//        if(chat != null) {
//            if(chat.isChatDeletedByUser(iam.getId())) {
//                daoChat.setDeletedToUser(Long chatId,iam.getId(), false);
//                daoChat.setFromDate(Long chatId,iam.getId(), nowDate);
//            }
//
//            List<Message> messages = daoChat.getMessages(chat.getId(), int numberPage)
//            MessageStory.showHostoryMessageToUser(messages, iam.getId());
//
//        }
//        else if(chat==null) {
//            //создать чат
//            //поместить user_id и chat_id в user_chat_db
//        }
//
//        return "direct";
//    }


}
