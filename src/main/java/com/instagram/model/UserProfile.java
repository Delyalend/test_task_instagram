package com.instagram.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    private long id;
    private int countPosts;
    private int countFollowers;
    private int countFollows;
    private String username;
    private String name;
}
