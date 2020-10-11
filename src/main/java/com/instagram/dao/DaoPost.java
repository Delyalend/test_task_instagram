package com.instagram.dao;

import com.instagram.dto.DtoPostPreview;
import com.instagram.model.Post;

import java.util.List;

public interface DaoPost {
    Long createPost(Post post);
    List<Post> getPosts(Long userId, Long postOffset);
    void addCommentToPost(Long commentId, Long postId);
    List<DtoPostPreview> getDtoPostsPreview(Long userId, Long postOffset, Long count);
    Post getPostById(Long postId);
}
