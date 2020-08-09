package com.instagram.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String password;
    private String name;
    private String username;
    private String mail;
    private String website;
    private String avatar;
    private String description;
    private String number;
    private String gender;
    private Date birthday;
    private boolean enabled;
    private Role role;
    private List<Subscriber> subscribers;
    private List<Subscription> subscriptions;
}
