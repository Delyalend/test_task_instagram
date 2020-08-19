package com.instagram.dao;

import com.instagram.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
public class DaoMessageImpl implements DaoMessage {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    //language=SQL
    private final String INSERT_INTO_MESSAGE_DB = "insert into message_db(chat_id, owner_id, content, type, date) values (?,?,?,?,now())";


    @Override
    public void createMessage(Long chatId, Long owner_id, String content, String typeContent) {
        jdbcTemplate.update(INSERT_INTO_MESSAGE_DB, chatId, owner_id, content, typeContent);
    }


    //language=SQL
    private final String SELECT_FROM_MESSAGE_CHAT_DB_BY_CHAT_ID = "select * from message_db where message_db.chat_id = ? " +
            "order by message_db.date offset ? limit ?";
    
    private RowMapper<Message> ROW_MAPPER_TO_MESSAGE_LIST = (ResultSet resultSet, int rowNum) -> {
        return Message.builder()
                .id(resultSet.getLong("id"))
                .chat_id(resultSet.getLong("owner_id"))
                .chat_id(resultSet.getLong("chat_id"))
                .content(resultSet.getString("content"))
                .type(resultSet.getString("type"))
                .date(resultSet.getString("date"))
                .build();
    };


    @Override
    public List<Message> getMessagesByChatId(Long chatId, int offset) {
        int limit = 30;
        return jdbcTemplate.query(SELECT_FROM_MESSAGE_CHAT_DB_BY_CHAT_ID,ROW_MAPPER_TO_MESSAGE_LIST,chatId,offset, limit);
    }


}
