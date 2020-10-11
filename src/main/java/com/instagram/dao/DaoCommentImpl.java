package com.instagram.dao;

import com.instagram.model.Comment;
import com.instagram.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class DaoCommentImpl implements DaoComment {

    @Autowired
    private DaoUser daoUser;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private final String CREATE_COMMENT = "insert into comment_db(owner_id,text,date) values(?,?,NOW()::timestamp) returning id";

    //language=SQL
    private final String SELECT_COMMENTS_BY_POST_ID = "select c_db.* from comment_db c_db " +
            "left join post_comment_db p_c_db on p_c_db.comment_id = c_db.id where p_c_db.post_id = ? order by c_db.date";

    private RowMapper<Comment> ROW_MAPPER_COMMENT = (ResultSet resultSet, int rowNum) -> {
        User user = daoUser.getUserById(resultSet.getLong("owner_id"));
        Comment c = Comment.builder()
                .id(resultSet.getLong("id"))
                .user(user)
                .text(resultSet.getString("text"))
                .date(resultSet.getDate("date"))
                .build();
        System.out.println(c.getDate());
        return c;
    };

    private RowMapper<Long> ROW_MAPPER_TO_LONG = (ResultSet resultSet, int rowNum) -> {
        return resultSet.getLong("id");
    };

    @Override
    public Long createComment(Comment comment) {
        return jdbcTemplate.query(CREATE_COMMENT,ROW_MAPPER_TO_LONG, comment.getUser(),comment.getText()).get(0);
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return jdbcTemplate.query(SELECT_COMMENTS_BY_POST_ID, ROW_MAPPER_COMMENT, postId);
    }

}
