package com.instagram.dto;

import com.instagram.model.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoPostPreview {
    private Image image;
    private Long countLikes;
    private Long countComments;
}
