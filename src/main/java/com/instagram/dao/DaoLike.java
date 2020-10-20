package com.instagram.dao;

public interface DaoLike {
    void addLikeToPost(Long userId, Long postId);
    void removeLikeToPost(Long userId, Long postId);
    Long getLikeIdByUserIdAndPostId(Long userId, Long postId);
}
