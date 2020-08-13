package com.instagram.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    public enum TYPE {text, image, video}

    private String usernameOwner;
    private String text;
    private String date;
    private TYPE type;
}
