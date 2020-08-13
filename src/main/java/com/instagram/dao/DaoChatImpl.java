package com.instagram.dao;

import com.instagram.dto.DtoChat;
import com.instagram.model.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
public class DaoChatImpl implements DaoChat {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private final String SELECT_CHATS_BY_USER_ID = "select username, avatar from user_chat_db " +
            "left join user_db on user_db.id = user_chat_db.user_id " +
            "where user_db.username = ?";
    //language=SQL
    private final String SELECT_CHAT_IDS_BY_USERNAME = "select chat_id from user_chat_db " +
            "where user_id=? and deleted=false";
    //language=SQL
    private final String SELECT_CHAT_IDS_BY_USER_ID = "select chat_id from user_chat_db where user_id = ? and deleted=false";
    //language=SQL
    private final String SELECT_CHAT_BY_CHAT_ID = "select * from chat_db where id = ?";
    //language=SQL
    private final String SELECT_DELETED_FROM_USER_CHAT_DB = "select deleted from user_chat_db where user_id = ? and chat_id = ?";
    //language=SQL
    private final String UPDATE_DELETED_TO_TRUE = "update user_chat_db set deleted = true where chat_id = ? and user_id = ?";
    //language=SQL
    private final String UPDATE_DELETED_TO_FALSE = "update user_chat_db set deleted = false, was_deleted = now() where chat_id = ? and user_id = ?";

    private RowMapper<DtoChat> ROW_MAPPER_LIST_CHAT = (ResultSet resultSet, int rowNum) -> {

        return DtoChat.builder()
                .username(resultSet.getString("username"))
                .avatar(resultSet.getString("avatar"))
                .build();
    };

    private RowMapper<Long> ROW_MAPPER_TO_LONG_LIST = (ResultSet resultSet, int rowNum) -> {
        return resultSet.getLong("chat_id");
    };

    private RowMapper<Chat> ROW_MAPPER_TO_CHAT = (ResultSet resultSet, int rowNum) -> {
        return Chat.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .build();
    };

    private RowMapper<Boolean> ROW_MAPPER_TO_BOOLEAN = (ResultSet resultSet, int rowNum) -> {
        return resultSet.getBoolean("deleted");
    };


    @Override
    public List<Long> getChatIdsByUsername(String username) {
        return jdbcTemplate.query(SELECT_CHAT_IDS_BY_USERNAME, ROW_MAPPER_TO_LONG_LIST, username);
    }

    @Override
    public List<DtoChat> getChatsByUsername(String username) {
        return jdbcTemplate.query(SELECT_CHATS_BY_USER_ID, ROW_MAPPER_LIST_CHAT, username);
    }

    @Override
    public Chat getChatByUsersId(Long userId1, Long userId2) {
        return null;
    }

    @Override
    public List<Long> getChatIdsByUserId(Long userId) {
        return jdbcTemplate.query(SELECT_CHAT_IDS_BY_USER_ID, ROW_MAPPER_TO_LONG_LIST, userId);
    }

    @Override
    public Chat getChatByChatId(Long chatId) {
        List<Chat> chats = jdbcTemplate.query(SELECT_CHAT_BY_CHAT_ID, ROW_MAPPER_TO_CHAT, chatId);
        if (!chats.isEmpty()) {
            return chats.get(0);
        }
        return null;
    }

    @Override
    public boolean isChatDeleted(Long iamId, Long chatId) {
        List<Boolean> booleans = jdbcTemplate.query(SELECT_DELETED_FROM_USER_CHAT_DB, ROW_MAPPER_TO_BOOLEAN, iamId, chatId);
        return booleans.get(0);
    }


    //language=SQL
    private final String INSERT_INTO_CHAT_DB = "insert into chat_db returning id";

    @Override
    public Long createChat(Long iamId, Long opponentId) {
        return ((long) jdbcTemplate.update(INSERT_INTO_CHAT_DB));
    }

    @Override
    public void setDeletedToTrue(Long chatId, Long userId) {
        jdbcTemplate.update(UPDATE_DELETED_TO_TRUE, chatId, userId);

    }

    @Override
    public void setDeletedToFalse(Long chatId, Long userId) {
        jdbcTemplate.update(UPDATE_DELETED_TO_FALSE, chatId, userId);

    }

}
