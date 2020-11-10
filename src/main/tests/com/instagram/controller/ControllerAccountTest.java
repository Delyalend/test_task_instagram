package com.instagram.controller;


import com.instagram.service.ServicePageTitle;
import com.instagram.service.ServiceSubscription;
import com.instagram.service.ServiceUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ControllerAccount.class)
@AutoConfigureMockMvc
public class ControllerAccountTest {

    @Autowired
    private MockMvc mockMvc;

//    @Autowired
//    private WebApplicationContext context;
//
//    @Before
//    private void setUp() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//    }

    @MockBean
    private ServiceUser serviceUser;

    @MockBean
    private ServicePageTitle servicePageTitle;

    @MockBean
    private ServiceSubscription serviceSubscription;

    @Autowired
    private ControllerAccount controllerAccount;

    @MockBean
    private Model model;
    

    @Test
    public void getUserProfile() throws Exception {
        assertThat(mockMvc.perform(get("/{username}/", "test")
                .contentType(MediaType.TEXT_HTML)).andReturn().getResponse().getStatus() == 200);

    }

    @Test
    public void getFollows() {
    }

    @Test
    public void getFollowers() {
    }

    @Test
    public void getUsers() {
    }
}