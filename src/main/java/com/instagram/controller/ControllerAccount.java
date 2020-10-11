package com.instagram.controller;

import com.instagram.dao.DaoSub;
import com.instagram.dao.DaoUser;
import com.instagram.dto.DtoUserForSubs;
import com.instagram.model.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControllerAccount {

    @Autowired
    private DaoUser daoUser;

    @Autowired
    private DaoSub daoSub;

    @GetMapping("/{username}/")
    public String getAccountProfile(@PathVariable String username, Model model, Authentication authentication) throws Exception {

        User user = daoUser.getUserByUsername(username);

        if(user == null) {
            throw new Exception("user is not found");
        }

        model.addAttribute("user", user);

        if (user.getUsername().equals(authentication.getName())) {
            return "personalProfile";
        }

        String title;
        if (user.getName().equals("")) {
            title = "@" + user.getUsername() + " · Фото и видео в Instagram";
        } else {
            title = user.getName() + " (@" + user.getUsername() + ") · Фото и видео в Instagram";
        }
        model.addAttribute("title", title);


        model.addAttribute("isSub", daoSub.isFollower(authentication.getName(), user.getUsername()));


        return "profile";
    }

    @GetMapping("/{username}/follows/{page}")
    public @ResponseBody
    List<DtoUserForSubs> getMoreFollows(@PathVariable int page, Authentication authentication) {
        User iam = daoUser.getUserByUsername(authentication.getName());

        List<User> usersFromDb = daoSub.findFollowsByFollowerId(iam.getId(), page);

        List<DtoUserForSubs> users = new ArrayList();
        for (int i = 0; i < usersFromDb.size(); i++) {
            DtoUserForSubs user = new DtoUserForSubs();
            user.setId(usersFromDb.get(i).getId());
            user.setName(usersFromDb.get(i).getName());
            user.setUsername(usersFromDb.get(i).getUsername());
            user.setAvatar(usersFromDb.get(i).getAvatar());
            users.add(user);
        }

        return users;
    }


    @GetMapping("/{username}/followers/{page}")
    public @ResponseBody
    List<DtoUserForSubs> getMoreFollowers(@PathVariable int page, Authentication authentication) {

        User iam = daoUser.getUserByUsername(authentication.getName());

        List<User> usersFromDb = daoSub.findFollowersByFollowId(iam.getId(), page);

        List<DtoUserForSubs> users = new ArrayList();
        for (int i = 0; i < usersFromDb.size(); i++) {
            DtoUserForSubs user = new DtoUserForSubs();
            user.setId(usersFromDb.get(i).getId());
            user.setName(usersFromDb.get(i).getName());
            user.setUsername(usersFromDb.get(i).getUsername());
            user.setAvatar(usersFromDb.get(i).getAvatar());
            users.add(user);
        }

        return users;
    }

    @GetMapping("/{username}/users/{text}")
    public @ResponseBody
    List<DtoUserForSubs> getMoreUsers(@PathVariable String text, Authentication authentication) {
        User iam = daoUser.getUserByUsername(authentication.getName());

        //List<User> usersFromDb = daoSub.findUsersByNameOrUsername(iam.getId(),text);//нужен специальный сервис!
        List<User> usersFromDb = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User usr = User.builder()
                    .username("bla-bla")
                    .name("name-name")
                    .avatar("avatar")
                    .id(20000001L)
                    .enabled(true)
                    .build();
            usersFromDb.add(usr);
        }

        List<DtoUserForSubs> users = new ArrayList();
        for (int i = 0; i < usersFromDb.size(); i++) {
            DtoUserForSubs user = new DtoUserForSubs();
            user.setId(usersFromDb.get(i).getId());
            user.setName(usersFromDb.get(i).getName());
            user.setUsername(usersFromDb.get(i).getUsername());
            user.setAvatar(usersFromDb.get(i).getAvatar());
            users.add(user);
        }

        return users;
    }
}
