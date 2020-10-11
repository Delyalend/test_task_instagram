package com.instagram.service;

import com.instagram.dao.DaoComment;
import com.instagram.dao.DaoImage;
import com.instagram.dao.DaoPost;
import com.instagram.model.Comment;
import com.instagram.model.Image;
import com.instagram.model.Post;
import com.instagram.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@PropertySource("classpath:application.properties")
public class ServicePostImpl implements ServicePost {

    @Autowired
    private ServiceImage serviceImage;

    @Autowired
    private ServiceComment serviceComment;

    @Autowired
    private DaoPost daoPost;

    @Autowired
    private DaoImage daoImage;

    @Autowired
    private DaoComment daoComment;

    @Value("${application.maxImageWidth}")
    private int imgWidth;

    @Value("${application.maxImageHeight}")
    private int maxImageHeight;

    @Value("${application.maxImageWidth}")
    private int maxImageWidth;

    @Value("${application.maxLengthComment}")
    private int maxLengthComment;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void savePost(Post post) {
        if (post.getImages().size() > 0) {
            Comment clearedComment = getClearedComment(post.getComment(), post.getOwner());
            post.setComment(clearedComment.getText());
            Long postId = daoPost.createPost(post);
            addImagesToPost(post.getImages(), postId, post.getOwner().getId());
        }
    }

    private boolean postIsEmpty(Post post) {
        return post.getComment().length() <= 0 && post.getImages().size() <= 0;
    }

    private void addImagesToPost(List<Image> images, Long postId, Long ownerId) {
        List<Image> reducedImages = serviceImage.getReducedImages(images, maxImageWidth, maxImageHeight);
        reducedImages.forEach(img -> {
            img.setPostId(postId);
            img.setOwnerId(ownerId);
            daoImage.createImage(img);
        });
    }

    private Comment getClearedComment(String commentText, User user) {
        String clearedTxt = serviceComment.getClearedComment(commentText);
        String reducedClearedTxt = serviceComment.getReducedComment(clearedTxt, maxLengthComment);
        return Comment.builder()
                .text(reducedClearedTxt)
                .user(user)
                .build();
    }
}
