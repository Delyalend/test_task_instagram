package com.instagram.dao;

import com.instagram.model.Chat;

import java.util.List;

public interface DaoChat {


    Chat getChatByChatId(Long chatId);

    boolean isChatDeleted(Long iamId, Long chatId);

    Long createChat(Long iamId, Long opponentId);

    void setDeletedToTrue(Long chatId, Long userId);

    void setDeletedToFalse(Long chatId, Long userId);

    List<Chat> getChatsByUserId(Long userId);

    List<Chat> getChatsByUsername(String username);
}
