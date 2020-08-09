package com.instagram.dto;

import com.instagram.model.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;

@Data
public class DtoRegistration {
    private String mail;
    private String name;
    private String username;
    private String password;
    private Date birthday;

    public User toUser(PasswordEncoder passwordEncoder) {
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .mail(mail)
                .birthday(birthday)
                .enabled(true)
                .build();
        if (name != null) {
            user.setName("");
        }
        System.out.println("Я здесь");
        return user;
    }
}
