package com.instagram.controller;

import com.instagram.dao.DaoLike;
import com.instagram.dao.DaoUser;
import com.instagram.model.User;
import com.instagram.service.ServiceLike;
import com.instagram.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ControllerLike {

    @Autowired
    private ServiceUser serviceUser;

    @Autowired
    private ServiceLike serviceLike;

    @GetMapping("post/{postId}/like/")
    public void addLikeToPost(Authentication authentication, @PathVariable Long postId) {
        try {
            User iam = serviceUser.getUserByUsername(authentication.getName());
            serviceLike.addLikeToPost(iam.getId(), postId);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


}
