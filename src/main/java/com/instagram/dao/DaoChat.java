package com.instagram.dao;

import com.instagram.model.Chat;

import java.util.List;

public interface DaoChat {

    boolean hasUsersChat(Long userId1, Long userId2);

    List<Chat> getChatsByUserId(Long userId);

    Chat getChatByUsersId(Long userId1, Long userId2);

}
