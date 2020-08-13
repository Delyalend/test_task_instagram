package com.instagram.dao;

import com.instagram.model.Message;

public interface DaoMessage {

    Long createMessage(String content, Message.TYPE typeContent, Long chatId);

    void addMessageToChat(Long chatId, Long msgId);
}
