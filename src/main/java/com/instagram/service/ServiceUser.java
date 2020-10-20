package com.instagram.service;

import com.instagram.model.User;
import com.instagram.model.UserProfile;
import org.springframework.security.core.Authentication;
import org.springframework.web.client.HttpClientErrorException;

public interface ServiceUser {
    User getUserByUsername(String username);

    UserProfile getUserProfile(String username, Authentication auth);

    boolean isFollower(String usernameFollower, String usernameFollows);
}
