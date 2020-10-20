package com.instagram.service;

import org.springframework.stereotype.Service;

@Service
public class ServicePageTitleImpl implements ServicePageTitle {

    @Override
    public String getPageTitle(String username, String name) {
        String title;
        if (name.equals("")) {
            title = "@" + username + " · Фото и видео в Instagram";
        } else {
            title = name + " (@" + username + ") · Фото и видео в Instagram";
        }
        return title;
    }

}
