package com.instagram.dao;

import com.instagram.dto.DtoChat;
import com.instagram.model.Chat;
import com.instagram.model.User;

import java.util.List;

public interface DaoChat {

    List<Long> getChatIdsByUsername(String username);

    boolean hasUsersChat(Long userId1, Long userId2);

    List<DtoChat> getChatsByUsername(String username);

    Chat getChatByUsersId(Long userId1, Long userId2);

    List<Long> getChatIdsByUserId(Long userId);

}
