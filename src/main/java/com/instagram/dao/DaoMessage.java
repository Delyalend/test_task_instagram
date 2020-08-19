package com.instagram.dao;

import com.instagram.model.Message;

import java.util.List;

public interface DaoMessage {

    void createMessage(Long chatId, Long owner_id, String content, String typeContent);

    List<Message> getMessagesByChatId(Long chatId, int offset);

}
