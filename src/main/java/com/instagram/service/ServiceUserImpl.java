package com.instagram.service;

import com.instagram.dao.DaoPost;
import com.instagram.dao.DaoSub;
import com.instagram.dao.DaoUser;
import com.instagram.model.User;
import com.instagram.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service

public class ServiceUserImpl implements ServiceUser {

    @Autowired
    private DaoUser daoUser;

    @Autowired
    private DaoSub daoSub;

    @Autowired
    private DaoPost daoPost;

    @Override
    public User getUserByUsername(String username) {
        return daoUser.getUserByUsername(username);
    }

    @Override

    public UserProfile getUserProfile(String username, Authentication auth) {
        User usr = getUserByUsername(username);
        if(usr == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found'");
        }

        int countFollowers = daoSub.getCountFollowers(usr.getId());
        int countFollows = daoSub.getCountFollows(usr.getId());
        int countPosts = daoPost.getCountPosts(usr.getId());

        return UserProfile.builder()
                .id(usr.getId())
                .username(usr.getUsername())
                .name(usr.getName())
                .countFollowers(countFollowers)
                .countFollows(countFollows)
                .countPosts(countPosts)
                .build();
    }

    @Override
    public boolean isFollower(String usernameFollower, String usernameFollows) {
        return daoSub.isFollower(usernameFollower, usernameFollows);
    }
}
