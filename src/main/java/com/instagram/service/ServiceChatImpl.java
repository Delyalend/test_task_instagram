package com.instagram.service;

import com.instagram.dao.DaoChat;
import com.instagram.dao.DaoUser;
import com.instagram.dto.DtoChat;
import com.instagram.model.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceChatImpl implements ServiceChat {

    @Autowired
    private DaoUser daoUser;

    @Autowired
    private DaoChat daoChat;


    @Override
    public List<DtoChat> sendChatsToUser(@DestinationVariable String username) {
        return getDtoChats(username);
    }


    private List<DtoChat> getDtoChats(String username) {
        //Из полученных чатов надо составить dto
        List<Chat> chats = getChatsByUsername(username);

        List<DtoChat> dtoChats = new ArrayList<>();
        chats.forEach(chat -> {
            chat.getUsers().forEach(user -> {
                //ищем оппонента по переписке
                if (!user.getUsername().equals(username)) {
                    dtoChats.add(DtoChat.builder()
                            .chatId(chat.getId())
                            .avatar(user.getAvatar())
                            .username(user.getUsername())
                            .build());
                }
            });
        });
        return dtoChats;
    }

    private List<Chat> getChatsByUsername(String username) {

        Long userId = daoUser.getUserByUsername(username).getId();
        List<Chat> chats = new ArrayList<>();

        daoChat.getChatsByUsername(username).forEach(chat -> {
            if (!chatDeleted(userId, chat.getId())) {
                chats.add(chat);
            }
        });

        return chats;
    }

    private boolean chatDeleted(Long iamId, Long chatId) {
        return daoChat.isChatDeleted(iamId, chatId);
    }


}
