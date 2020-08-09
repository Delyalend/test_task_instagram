package com.instagram.controller;

import com.instagram.dao.DaoChat;
import com.instagram.dao.DaoUser;
import com.instagram.model.Chat;
import com.instagram.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ControllerChat {

    @Autowired
    private DaoChat daoChat;

    @Autowired
    private DaoUser daoUser;

    @GetMapping("/direct/chatWithUser/{userId}")
    public String getChat(Authentication authentication, @PathVariable Long userId) {

        User iam = daoUser.findUserWithoutRoleByUsername(authentication.getName());

        Chat chat = daoChat.getChatByUsersId(iam.getId(), userId);

        return "chat";
    }


}
