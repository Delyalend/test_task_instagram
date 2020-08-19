package com.instagram.controller;

import com.instagram.dto.DtoMessage;
import com.instagram.model.Message;
import com.instagram.service.ServiceMessageHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ControllerMessageHistory {

    @Autowired
    private ServiceMessageHistory serviceMessageHistory;
    @GetMapping("chat/{chatId}/messageHistory/{offset}")
    public @ResponseBody
    List<Message> getMessageHistory(@PathVariable Long chatId, @PathVariable int offset) {
        System.out.println("chatId: " + chatId + " offset: " + offset);
        return serviceMessageHistory.getMessageHistory(chatId, offset);
    }

}
