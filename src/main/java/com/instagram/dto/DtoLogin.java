package com.instagram.dto;

import com.instagram.model.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class DtoLogin {
    private String username;
    private String password;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
    }
}
