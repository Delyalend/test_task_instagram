package com.instagram.controller;

import com.instagram.dao.DaoComment;
import com.instagram.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ControllerComment {

    @Autowired
    private DaoComment daoComment;

    @GetMapping("/post/{postId}/comments")
    public @ResponseBody
    List<Comment> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = daoComment.getCommentsByPostId(postId);
        comments.forEach(comment -> {
            comment.getUser().setPassword(null);
            comment.getUser().setWebsite(null);
            comment.getUser().setMail(null);
        });
        return comments;
    }
}
