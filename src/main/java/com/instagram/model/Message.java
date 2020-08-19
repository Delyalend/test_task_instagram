package com.instagram.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private Long id;
    private Long chat_id;
    private Long owner_id;
    private String content;
    private String type;
    private String date;
}
