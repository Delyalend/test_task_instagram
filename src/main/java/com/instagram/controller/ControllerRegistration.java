package com.instagram.controller;

import com.instagram.dao.DaoUser;
import com.instagram.dto.DtoRegistration;
import com.instagram.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControllerRegistration {

    @Autowired
    private DaoUser daoUser;

    @GetMapping("accounts/emailsignup")
    public String getRegistration() {
        return "registration";
    }

    @PostMapping("accounts/emailsignup")
    public String registerUserStage1(DtoRegistration dtoRegistration, Model model) {

        User user = dtoRegistration.toUser(new BCryptPasswordEncoder());

        if (daoUser.findUserWithoutRoleByUsername(user.getUsername()) == null) {
            daoUser.createUser(user);
            return "redirect:/accounts/login";
        }
        model.addAttribute("error", "NicknameIsAlreadyUsed");
        return "redirect:/accounts/emailsignup";
    }


}
