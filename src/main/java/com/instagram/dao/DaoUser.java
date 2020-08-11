package com.instagram.dao;

import com.instagram.model.User;

import java.util.List;

public interface DaoUser {

    //find
    User findUserWithoutRoleByUsername(String username);
    User findUserWithRoleByUsername(String username);
    List<Long> getOpponentUserIdsByChatIds(List<Long> chatIds, Long userId);


    User findUserWithoutRoleById(Long id);

    //create
    void createUser(User user);
}
