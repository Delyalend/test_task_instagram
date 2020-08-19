package com.instagram.service;

import com.instagram.dao.DaoMessage;
import com.instagram.dto.DtoMessage;
import com.instagram.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ServiceMessageHistoryImpl implements ServiceMessageHistory {

    @Autowired
    private DaoMessage daoMessage;

    @Override
    public List<Message> getMessageHistory(long chatId, int offset) {
        return daoMessage.getMessagesByChatId(chatId, offset);
    }

    @Override
    public void saveMessage(Long chatId, Long ownerId, String content, String type) {
        daoMessage.createMessage(chatId,ownerId,content,type);
    }


}
