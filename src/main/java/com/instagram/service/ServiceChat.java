package com.instagram.service;

import com.instagram.dto.DtoChat;
import com.instagram.model.Chat;
import com.instagram.model.Message;

import java.util.List;

public interface ServiceChat {


    List<DtoChat> getDtoChatsByUsername(String username);

    boolean hasUsersChat(Long iamId, Long opponentId);

    boolean isChatDeleted(Long iamId, Long chatId);

    Chat getChatByUserIds(Long user1, Long user2);

    Message sendMessage(String msgJSON, String username);

}
