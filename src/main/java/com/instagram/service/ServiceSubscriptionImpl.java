package com.instagram.service;

import com.instagram.dao.DaoSub;
import com.instagram.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceSubscriptionImpl implements ServiceSubscription {

    @Autowired
    private DaoSub daoSub;

    @Override
    public boolean isFollower(String usernameFollower, String usernameFollows) {
        return daoSub.isFollower(usernameFollower,usernameFollows);
    }

    @Override
    public List<User> findFollowersByFollowId(Long followId, int page) {
        return daoSub.findFollowersByFollowId(followId,page);
    }

    @Override
    public List<User> findFollowsByFollowerId(Long followerId, int page) {
        return daoSub.findFollowsByFollowerId(followerId, page);
    }
}
