package com.instagram.service;

import com.instagram.dao.DaoChat;
import com.instagram.dao.DaoUser;
import com.instagram.dto.DtoChat;
import com.instagram.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceChatImpl implements ServiceChat {

    @Autowired
    private DaoUser daoUser;


    @Autowired
    private DaoChat daoChat;

    public List<User> getOpponentsForUserByUsername(String username) {
        //Находим юзера, которому нужны чаты
        User user = daoUser.findUserWithoutRoleByUsername(username);
        System.out.println("вот найденный юзер: " + user.getName());

        //Находим список id чатов, в которых состоит юзер
        List<Long> chatIdsByUserId = daoChat.getChatIdsByUserId(user.getId());
        System.out.println("Вот список id чатов, в которых состоит юзер: " + chatIdsByUserId);


        //Берем из чатов id юзеров-оппонентов
        List<Long> opponentUserIds = daoUser.getOpponentUserIdsByChatIds(chatIdsByUserId, user.getId());
        System.out.println("юзеры оппоненты ids: " + opponentUserIds);


        //Получаем при помощи id юзеров модель User
        List<User> usersFromChats = new ArrayList<>();
        opponentUserIds.forEach(opponent -> {
            usersFromChats.add(daoUser.findUserWithoutRoleById(opponent));
        });
        System.out.println("а вот и юзвери!"+usersFromChats);
        return usersFromChats;
    }

    @Override
    public List<DtoChat> getDtoChatsByUsername(String username) {
        List<User> usersFromChats = getOpponentsForUserByUsername(username);
        //Преобразуем юзеров в dto chat
        List<DtoChat> dtoChats = new ArrayList<>();
        usersFromChats.forEach(userFromChat -> {
            dtoChats.add(DtoChat.builder().username(userFromChat.getUsername()).avatar(userFromChat.getAvatar()).build());
        });
        return dtoChats;
    }


}
