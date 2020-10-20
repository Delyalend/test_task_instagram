package com.instagram.controller;

import com.instagram.dto.DtoUserForSubs;
import com.instagram.model.User;
import com.instagram.model.UserProfile;
import com.instagram.service.ServicePageTitle;
import com.instagram.service.ServiceSubscription;
import com.instagram.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControllerAccount {

    @Autowired
    private ServiceUser serviceUser;

    @Autowired
    private ServicePageTitle servicePageTitle;

    @Autowired
    private ServiceSubscription serviceSubscription;

    @GetMapping("/{username}/")
    public String getUserProfile(@PathVariable String username, Model model, Authentication auth) throws Exception {
        UserProfile userProfile = serviceUser.getUserProfile(username, auth);
        String titlePage = servicePageTitle.getPageTitle(userProfile.getUsername(), userProfile.getName());
        boolean isOwnerPage = userProfile.getUsername().equals(auth.getName());
        if (!isOwnerPage) {
            boolean isFollower = serviceUser.isFollower(auth.getName(), userProfile.getUsername());
            model.addAttribute("isFollower", isFollower);
        }
        model.addAttribute("isOwnerPage", isOwnerPage);
        model.addAttribute("titlePage", titlePage);
        model.addAttribute("userProfile", userProfile);
        return "userProfile";
    }

    @GetMapping("/{username}/follows/{page}")
    public @ResponseBody
    List<DtoUserForSubs> getFollows(@PathVariable int page, Authentication authentication) {
        User iam = serviceUser.getUserByUsername(authentication.getName());

        List<User> usersFromDb = serviceSubscription.findFollowsByFollowerId(iam.getId(), page);

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
    List<DtoUserForSubs> getFollowers(@PathVariable int page, Authentication authentication) {

        User iam = serviceUser.getUserByUsername(authentication.getName());

        List<User> followers = serviceSubscription.findFollowersByFollowId(iam.getId(), page);

        List<DtoUserForSubs> users = new ArrayList();

        followers.forEach(follower -> {
            DtoUserForSubs usr = new DtoUserForSubs();
            usr.setId(follower.getId());
            usr.setName(follower.getName());
            usr.setUsername(follower.getUsername());
            usr.setAvatar(follower.getAvatar());
            users.add(usr);
        });

        return users;
    }

    @GetMapping("/{username}/users/{text}")
    public @ResponseBody
    List<DtoUserForSubs> getUsers(@PathVariable String text, Authentication authentication) {
        User iam = serviceUser.getUserByUsername(authentication.getName());

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
