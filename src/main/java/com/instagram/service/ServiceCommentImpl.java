package com.instagram.service;

import org.springframework.stereotype.Service;

@Service
public class ServiceCommentImpl implements ServiceComment {
    @Override
    public String getReducedComment(String comment, int maxLength) {
        if (comment.length() > maxLength) {
            return comment.codePointCount(0, comment.length()) > 300 ?
                    comment.substring(0, comment.offsetByCodePoints(0, 300)) : comment;
        }
        return comment;
    }

    @Override
    public String getClearedComment(String comment) {
        comment = comment.replace("\n", " ");
        comment = comment.replace("\t", " ");
        while (comment.contains("  ")) {
            comment = comment.replace("  ", " ");
        }
        return comment;
    }
}
