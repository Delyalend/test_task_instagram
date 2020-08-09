package com.instagram.controller;

import com.instagram.dao.DaoUser;
import com.instagram.dto.DtoLogin;
import com.instagram.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControllerLogin {

    @Autowired
    private DaoUser daoUser;



    @GetMapping("accounts/login")
    public String getLogin1() {
        return "login";
    }

    @GetMapping("/login")
    public String getLogin2() {
        return "login";
    }

    @PostMapping("accounts/login")
    public String postLogin(DtoLogin dtoLogin, Model model) {

        User userFromDb = daoUser.findUserWithoutRoleByUsername(dtoLogin.getUsername());

        if (userFromDb == null) {
            model.addAttribute("error", "UserNotExistsOrPasswordIncorrect");
            return "login";
        }

        User user = dtoLogin.toUser(new BCryptPasswordEncoder());

        if (userFromDb.getPassword().equals(user.getPassword())) {
            return "redirect:/";
        }

        model.addAttribute("error", "UserNotExistsOrPasswordIncorrect");

        return "login";
    }

}
