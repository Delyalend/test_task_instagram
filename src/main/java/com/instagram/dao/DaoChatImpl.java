package com.instagram.dao;

import com.instagram.model.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DaoChatImpl implements DaoChat {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private final String SELECT_CHATS_BY_USER_ID = "";


    @Override
    public boolean hasUsersChat(Long userId1, Long userId2) {
        return false;
    }

    @Override
    public List<Chat> getChatsByUserId(Long userId) {

        List<Chat> chats = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            chats.add(Chat.builder().id(((long)i)).name("name").build());
        }

        return chats;

    }

    @Override
    public Chat getChatByUsersId(Long userId1, Long userId2) {
        return null;
    }
}
