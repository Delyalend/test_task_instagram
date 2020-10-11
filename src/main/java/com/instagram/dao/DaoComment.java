package com.instagram.dao;

import com.instagram.model.Comment;

import java.util.List;

public interface DaoComment {
    Long createComment(Comment comment);
    List<Comment> getCommentsByPostId(Long postId);
}
