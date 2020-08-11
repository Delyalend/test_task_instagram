package com.instagram.dao;

import com.instagram.dto.DtoChat;
import com.instagram.model.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class DaoChatImpl implements DaoChat {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private final String SELECT_CHATS_BY_USER_ID = "select username, avatar from user_chat_db " +
            "left join user_db on user_db.id = user_chat_db.user_id " +
            "where user_db.username = ?";

    private RowMapper<DtoChat> ROW_MAPPER_LIST_CHAT = (ResultSet resultSet, int rowNum) -> {

        return DtoChat.builder()
                .username(resultSet.getString("username"))
                .avatar(resultSet.getString("avatar"))
                .build();
    };

    @Override
    public boolean hasUsersChat(Long userId1, Long userId2) {
        return false;
    }

    @Override
    public List<DtoChat> getChatsByUsername(String username) {
        return jdbcTemplate.query(SELECT_CHATS_BY_USER_ID, ROW_MAPPER_LIST_CHAT, username);
    }

    @Override
    public Chat getChatByUsersId(Long userId1, Long userId2) {
        return null;
    }
}
