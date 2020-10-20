package com.instagram.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
public class DaoLikeImpl implements DaoLike {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private String CREATE_LIKE = "insert into like_db(user_id) values (?) returning id";

    //language=SQL
    private String INSERT_INTO_LIKE_POST = "insert into like_post_db values (?, ?)";

    //language=SQL
    private String SELECT_FROM_LIKE_POST_BY_USER_ID = "select like_db.id from like_post_db " +
            "left join like_db on like_db.id = like_post_db.like_id " +
            "where like_db.user_id = ? and post_id = ?";

//    //language=SQL
//    private void DELETE_LIKE_FROM_POST = "update like"

    private RowMapper<Long> ROW_MAPPER_TO_LONG = (ResultSet resultSet, int RowNum) -> {
        return resultSet.getLong("id");
    };

    @Override
    public void addLikeToPost(Long userId, Long postId) {
        jdbcTemplate.update(INSERT_INTO_LIKE_POST, postId);
    }

    @Override
    public void removeLikeToPost(Long userId, Long postId) {


    }

    /**
     * @return returns -1 if there is no like. Else returns any value > 0
     */
    @Override
    public Long getLikeIdByUserIdAndPostId(Long userId, Long postId) {
        List<Long> id = jdbcTemplate.query(SELECT_FROM_LIKE_POST_BY_USER_ID, ROW_MAPPER_TO_LONG, userId, postId);
        if(id.size() == 0) {
            return -1L;
        }
        return id.get(0);
    }
}
