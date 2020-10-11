package com.instagram.service;

import com.instagram.model.Message;

import java.util.List;

public interface ServiceMessageHistory {

    List<Message> getMessageHistory(long chatId, int offset);

    void saveMessage(Message msg);

}
