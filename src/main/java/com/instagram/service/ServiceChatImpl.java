package com.instagram.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instagram.dao.DaoChat;
import com.instagram.dao.DaoMessage;
import com.instagram.dao.DaoUser;
import com.instagram.dto.DtoChat;
import com.instagram.model.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceChatImpl implements ServiceChat {

    @Autowired
    private DaoUser daoUser;

    @Autowired
    private DaoMessage daoMsg;

    @Autowired
    private DaoChat daoChat;


    @Autowired
    private ObjectMapper mapper;

//    public List<User> getOpponentsForUserByUsername(String username) {
//        User usr = daoUser.getUserByUsername(username);
//
//        List<Long> chatIds = daoChat.getChatIdsByUserId(usr.getId());
//
//        List<Long> opponentIds = daoUser.getOpponentIds(chatIds, usr.getId());
//
//        List<User> users = new ArrayList<>();
//        opponentIds.forEach(opponent -> {
//            users.add(daoUser.getUserById(opponent));
//        });
//        return users;
//    }

//    @Override
//    public List<DtoChat> getDtoChatsByUsername(String username) {
//        List<User> users = getOpponentsForUserByUsername(username);
//        List<DtoChat> dtoChats = new ArrayList<>();
//        users.forEach(userFromChat -> {
//            dtoChats.add(DtoChat.builder()
//                    .username(userFromChat.getUsername())
//                    .avatar(userFromChat.getAvatar())
//                    .build());
//        });
//        return dtoChats;
//    }


//    @Override
//    public Chat getChatByUserIds(Long usr1, Long usr2) {
//        List<Long> iamChatIds = daoChat.getChatIdsByUserId(usr1);
//        List<Long> opponentChatIds = daoChat.getChatIdsByUserId(usr2);
//        for (int i = 0; i < iamChatIds.size(); i++) {
//            for (int j = 0; j < opponentChatIds.size(); j++) {
//                if (iamChatIds.get(i).equals(opponentChatIds.get(j))) {
//                    return daoChat.getChatByChatId(iamChatIds.get(i));
//                }
//            }
//        }
//        return null;
//    }

//    @Override
//    @SneakyThrows
//    public Message sendMessage(String msgJSON, @DestinationVariable String username) {
//        Message msg = mapper.readValue(msgJSON, Message.class);
//
//        User iam = daoUser.getUserByUsername(msg.getUsernameOwner());
//        User opponent = daoUser.getUserByUsername(username);
//
//        if (!hasUsersChat(iam.getId(), opponent.getId())) {
//            Long chatId = daoChat.createChat(iam.getId(), opponent.getId());
//            daoMsg.createMessage(msg.getText(), msg.getType(), chatId, iam.getId());
//        } else {
//            Chat chat = getChatByUserIds(iam.getId(), opponent.getId());
//            if (isChatDeleted(iam.getId(), chat.getId())) {
//                daoChat.setDeletedToFalse(chat.getId(), iam.getId());
//            }
//            daoMsg.createMessage(msg.getText(), msg.getType(), chat.getId(), iam.getId());
//        }
//        return msg;
//    }

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


//    @Override
//    public boolean hasUsersChat(Long iamId, Long opponentId) {
//        List<Long> iamChatIds = daoChat.getChatIdsByUserId(iamId);
//        List<Long> opponentChatIds = daoChat.getChatIdsByUserId(opponentId);
//
//        for (Long iamChatId : iamChatIds) {
//            for (Long opponentChatId : opponentChatIds) {
//                if (iamChatId.equals(opponentChatId)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }


}
