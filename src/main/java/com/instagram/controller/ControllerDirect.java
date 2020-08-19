package com.instagram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instagram.dao.DaoUser;
import com.instagram.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ControllerDirect {

    @Autowired
    private DaoUser daoUser;

    @GetMapping("/direct/t/{chatId}")
    public String getChatId() {
        return "redirect:/direct/inbox";
    }

    @GetMapping("/direct/inbox")
    public String getDirect(Authentication auth, Model model) {
        User iam = daoUser.getUserByUsername(auth.getName());
        model.addAttribute("iam", iam);
        return "direct";
    }

    @GetMapping("/direct/{username}")
    public String chatSelector(Authentication auth, @PathVariable String username, Model model) {
        User iam = daoUser.getUserByUsername(auth.getName());
        User opponent = daoUser.getUserByUsername(username);
        model.addAttribute("iam", iam);
        model.addAttribute("opponent", opponent);
        return "direct";
    }

}
