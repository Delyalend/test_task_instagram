package com.instagram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instagram.dao.DaoPost;
import com.instagram.dao.DaoUser;
import com.instagram.dto.DtoPostPreview;
import com.instagram.model.Image;
import com.instagram.model.Post;
import com.instagram.model.User;
import com.instagram.service.ServiceImage;
import com.instagram.service.ServicePost;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControllerPost {

    @Autowired
    private DaoUser daoUser;

    @Autowired
    private DaoPost daoPost;

    @Autowired
    private ServicePost servicePost;

    @Autowired
    private ServiceImage serviceImage;

    @Autowired
    private ObjectMapper objMapper;

    @Value("${application.maxImageSizeInPublications}")
    private int imgHeightForPostPreview;
    @Value("${application.maxImageSizeInPublications}")
    private int imgWidthForPostPreview;

    @Value("${application.maxImageHeightInPost}")
    private int imgHeightForPost;
    @Value("${application.maxImageWidthInPost}")
    private int imgWidthForPost;


    @SneakyThrows
    @PostMapping("/post")
    public @ResponseBody
    void savePost(@RequestBody String imgInBase64InJSON, Authentication auth) {
        User iam = daoUser.getUserByUsername(auth.getName());
        Post post = objMapper.readValue(imgInBase64InJSON, Post.class);
        post.setOwner(iam);
        servicePost.savePost(post);
    }

    @SneakyThrows
    @GetMapping("/getPreviewPosts/{username}/{count}/{offset}")
    public @ResponseBody
    List<DtoPostPreview> getPreviewPosts(@PathVariable String username, @PathVariable Long count, @PathVariable Long offset) {
        User usr = daoUser.getUserByUsername(username);
        List<DtoPostPreview> previewPosts = daoPost.getDtoPostsPreview(usr.getId(), offset, count);
        for (int i = 0; i < previewPosts.size(); i++) {
            List<Image> images = new ArrayList<>();
            images.add(previewPosts.get(i).getImage());
            List<Image> reducedImages = serviceImage.getReducedImages(images, imgWidthForPostPreview, imgHeightForPostPreview);
            previewPosts.get(i).setImage(reducedImages.get(0));
        }
        return previewPosts;
    }

    @SneakyThrows
    @GetMapping("/getPost/{postId}")
    public @ResponseBody
    Post getPost(@PathVariable Long postId) {
        Post post = daoPost.getPostById(postId);
        List<Image> reducedImages = serviceImage.getReducedImages(post.getImages(), imgWidthForPost, imgHeightForPost);
        post.setImages(reducedImages);
        return post;
    }


}
