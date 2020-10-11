package com.instagram.service;

public interface ServiceComment {
    String getReducedComment(String comment, int maxLength);
    String getClearedComment(String comment);
}
