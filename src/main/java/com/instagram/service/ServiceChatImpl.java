package com.instagram.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instagram.dao.DaoChat;
import com.instagram.dao.DaoMessage;
import com.instagram.dao.DaoUser;
import com.instagram.dto.DtoChat;
import com.instagram.model.Chat;
import com.instagram.model.Message;
import com.instagram.model.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceChatImpl implements ServiceChat {

    @Autowired
    private DaoUser daoUser;

    @Autowired
    private DaoMessage daoMsg;

    @Autowired
    private DaoChat daoChat;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private ObjectMapper mapper;

    public List<User> getOpponentsForUserByUsername(String username) {
        //Находим юзера, которому нужны чаты
        User user = daoUser.getUserByUsername(username);

        //Находим список id чатов, в которых состоит юзер
        List<Long> chatIdsByUserId = daoChat.getChatIdsByUserId(user.getId());

        //Берем из чатов id юзеров-оппонентов
        List<Long> opponentUserIds = daoUser.getOpponentUserIdsByChatIds(chatIdsByUserId, user.getId());

        //Получаем при помощи id юзеров модель User
        List<User> usersFromChats = new ArrayList<>();
        opponentUserIds.forEach(opponent -> {
            usersFromChats.add(daoUser.getUserById(opponent));
        });
        return usersFromChats;
    }

    @Override
    public List<DtoChat> getDtoChatsByUsername(String username) {
        List<User> usersFromChats = getOpponentsForUserByUsername(username);
        //Преобразуем юзеров в dto chat
        List<DtoChat> dtoChats = new ArrayList<>();
        usersFromChats.forEach(userFromChat -> {
            dtoChats.add(DtoChat.builder().username(userFromChat.getUsername()).avatar(userFromChat.getAvatar()).build());
        });
        return dtoChats;
    }


    @Override
    public Chat getChatByUserIds(Long user1, Long user2) {
        List<Long> chatIdsOfIam = daoChat.getChatIdsByUserId(user1);
        List<Long> chatIdsOfOpponent = daoChat.getChatIdsByUserId(user2);
        //Находим общие id чатов в итоге получаем чаты, которые есть у двух юзеров
        for (int i = 0; i < chatIdsOfIam.size(); i++) {
            for (int j = 0; j < chatIdsOfOpponent.size(); j++) {
                if (chatIdsOfIam.get(i).equals(chatIdsOfOpponent.get(j))) {
                    return daoChat.getChatByChatId(chatIdsOfIam.get(i));
                }
            }
        }

        return null;
    }

    //TODO:доделать метод
    @Override
    @SendTo("/topic/stompChat/{username}")
    @SneakyThrows
    public Message sendMessage(String msgJSON, @DestinationVariable String username) {
        Message msg = mapper.readValue(msgJSON, Message.class);

        User iam = daoUser.getUserByUsername(msg.getUsernameOwner());
        User frnd = daoUser.getUserByUsername(username);

        if (!hasUsersChat(iam.getId(), frnd.getId())) {
            Long chatId = daoChat.createChat(iam.getId(), frnd.getId());
            Long msgId = daoMsg.createMessage(msg.getText(), msg.getType(), chatId);
            daoMsg.addMessageToChat(chatId, msgId);
        }
        else {
            Chat chat = getChatByUserIds(iam.getId(), frnd.getId());
            if (isChatDeleted(iam.getId(), chat.getId())) {
                daoChat.setDeletedToFalse(chat.getId(),iam.getId());
            }
            Long msgId = daoMsg.createMessage(msg.getText(), msg.getType(), chat.getId());
            daoMsg.addMessageToChat(chat.getId(), msgId);
        }
        return msg;
    }


    @Override
    public boolean hasUsersChat(Long iamId, Long opponentId) {
        List<Long> chatIdsOfIam = daoChat.getChatIdsByUserId(iamId);
        List<Long> chatIdsOfOpponent = daoChat.getChatIdsByUserId(opponentId);
        //Находим общие id чатов в итоге получаем чаты, которые есть у двух юзеров
        for (Long aLong : chatIdsOfIam) {
            for (Long value : chatIdsOfOpponent) {
                if (aLong.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isChatDeleted(Long iamId, Long chatId) {
        return daoChat.isChatDeleted(iamId, chatId);

    }


}
