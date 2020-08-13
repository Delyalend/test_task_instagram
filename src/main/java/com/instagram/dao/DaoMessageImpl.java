package com.instagram.dao;

import com.instagram.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class DaoMessageImpl implements DaoMessage {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    //language=SQL
    private final String INSERT_INTO_MESSAGE_DB = "insert into ";

    @Override
    public Long createMessage(String content, Message.TYPE typeContent, Long chatId) {
        return null;
    }

    @Override
    public void addMessageToChat(Long chatId, Long msgId) {

    }




}
