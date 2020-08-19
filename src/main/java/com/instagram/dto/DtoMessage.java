package com.instagram.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoMessage {
    private Long id;
    private Long owner_id;
    private String content;
    private String type;
    private String date;
}
