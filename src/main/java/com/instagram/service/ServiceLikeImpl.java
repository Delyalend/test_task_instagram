package com.instagram.service;

import com.instagram.dao.DaoLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceLikeImpl implements ServiceLike {

    @Autowired
    private DaoLike daoLike;

    @Override
    public void addLikeToPost(Long userId, Long postId) {
        if(!isPostLiked(userId, postId)) {
            daoLike.addLikeToPost(userId, postId);
        }
    }

    @Override
    public boolean isPostLiked(Long userId, Long postId) {
        Long likeId = daoLike.getLikeIdByUserIdAndPostId(userId, postId);
        return likeId>-1;
    }

    @Override
    public void removeLikeFromPost(Long userId, Long postId) {
        if(isPostLiked(userId, postId)) {

        }
    }
}
