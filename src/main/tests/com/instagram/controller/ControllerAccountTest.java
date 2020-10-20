package com.instagram.controller;

import com.instagram.model.UserProfile;
import com.instagram.service.ServiceUser;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(ControllerAccount.class)
class ControllerAccountTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ServiceUser serviceUser;

    @InjectMocks
    private Authentication auth;

    @Test
    void getUserProfile() {
        Mockito.when(serviceUser.getUserProfile("username", auth)).thenReturn(
                UserProfile.builder()
                        .id(0)
                        .countFollowers(0)
                        .countFollows(0)
                        .countPosts(0)
                        .name("name")
                        .username("username")
                        .build()
        );
//        mockMvc.perform(get())

    }

    @Test
    void getFollows() {
    }

    @Test
    void getFollowers() {
    }

    @Test
    void getUsers() {
    }
}