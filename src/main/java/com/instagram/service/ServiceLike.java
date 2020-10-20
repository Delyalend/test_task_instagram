package com.instagram.service;

public interface ServiceLike {

    void addLikeToPost(Long userId, Long postId);
    boolean isPostLiked(Long userId, Long postId);
    void removeLikeFromPost(Long userId, Long postId);
}
