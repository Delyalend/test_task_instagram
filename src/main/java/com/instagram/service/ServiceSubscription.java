package com.instagram.service;

import com.instagram.model.User;

import java.util.List;

public interface ServiceSubscription {
    boolean isFollower(String usernameFollower, String usernameFollows);
    List<User> findFollowersByFollowId(Long followId, int page);
    List<User> findFollowsByFollowerId(Long followerId, int page);
}
