package com.instagram.controller;

import com.instagram.dao.DaoSub;
import com.instagram.dao.DaoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControllerSub {

    @Autowired
    private DaoSub daoSub;

    @Autowired
    private DaoUser daoUser;

    @PostMapping("/subscribe/{username}")
    public String subscribeToUser(@PathVariable String username, Authentication authentication, Model model) {

        daoSub.followToUserByUsername(username,authentication.getName());

        return "redirect:/{username}/";
    }

    @PostMapping("/unsubscribe/{username}")
    public String unsubscribeToUser(@PathVariable String username, Authentication authentication, Model model) {

        daoSub.unfollowUserByUsername(username,authentication.getName());

        return "redirect:/{username}/";
    }

}
