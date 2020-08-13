package com.instagram.controller;


import com.instagram.dao.DaoUser;
import com.instagram.dto.DtoUserForSubs;
import com.instagram.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControllerHome {

    @Autowired
    private DaoUser daoUser;


    @GetMapping("/")
    public String getHome(Model model) {
        return "news";
    }


    @RequestMapping(value = "/getMoreUsers/{page}", method = RequestMethod.GET)
    public @ResponseBody List<DtoUserForSubs> GetMoreUsers(@PathVariable Long page, Authentication authentication) {

        User iam = daoUser.getUserByUsername(authentication.getName());

        List<DtoUserForSubs> users = new ArrayList();
        for (int i = 0; i < 10; i++) {
            DtoUserForSubs user = new DtoUserForSubs();
            user.setId(1L + i);
            user.setName("Name" + i);
            user.setUsername("Username" + i);
            user.setAvatar("Avatar" + i);
            users.add(user);
        }

        return users;
    }

}
