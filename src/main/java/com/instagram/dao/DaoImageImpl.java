package com.instagram.dao;

import com.instagram.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DaoImageImpl implements DaoImage {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private final String CREATE_IMAGE = "insert into image_db(type, src, owner_id, date, post_id) values (?,?,?,now(),?)";

    @Override
    public void createImage(Image img) {
        jdbcTemplate.update(CREATE_IMAGE, img.getType(), img.getSrc(), img.getOwnerId(), img.getPostId());
    }

}
