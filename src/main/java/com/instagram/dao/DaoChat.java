package com.instagram.dao;

import com.instagram.dto.DtoChat;
import com.instagram.model.Chat;

import java.util.List;

public interface DaoChat {

//    List<Long> getChatIdsByUsername(String username);

    //List<DtoChat> getChatsByUsername(String username);

//    Chat getChatByUsersId(Long userId1, Long userId2);

//    List<Long> getChatIdsByUserId(Long userId);

    Chat getChatByChatId(Long chatId);

    boolean isChatDeleted(Long iamId, Long chatId);

    Long createChat(Long iamId, Long opponentId);

    void setDeletedToTrue(Long chatId, Long userId);

    void setDeletedToFalse(Long chatId, Long userId);

    List<Chat> getChatsByUserId(Long userId);
    List<Chat> getChatsByUsername(String username);
}
