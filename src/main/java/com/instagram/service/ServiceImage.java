package com.instagram.service;

import com.instagram.model.Image;

import java.util.List;

public interface ServiceImage {

    List<Image> getReducedImages(List<Image> images, int maxWidth, int maxHeight);
}
