package com.instagram.dao;

import com.instagram.model.User;

import java.util.List;

public interface DaoUser {

    //find
    User getUserByUsername(String username);
    User getUserByUsernameWithRole(String username);
    List<Long> getOpponentIds(List<Long> chatIds, Long userId);


    User getUserById(Long userId);

    //create
    void createUser(User user);
}
