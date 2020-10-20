package com.instagram.dao;

import com.instagram.model.User;

import java.util.List;

public interface DaoSub {

    void followToUserByUsername(String usernameSubscription, String usernameSubscriber);
    void unfollowUserByUsername(String usernameSubscription, String usernameSubscriber);

    boolean isFollower(String usernameSubscription, String usernameSubscriber);

    List<User> findFollowersByFollowId(Long followId, int page);
    List<User> findFollowsByFollowerId(Long followerId, int page);

    int getCountFollowers(Long userId);
    int getCountFollows(Long userId);

}
