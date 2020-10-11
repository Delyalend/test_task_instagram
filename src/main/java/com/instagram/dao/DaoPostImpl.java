package com.instagram.dao;

import com.instagram.dto.DtoPostPreview;
import com.instagram.model.Image;
import com.instagram.model.Post;
import com.instagram.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class DaoPostImpl implements DaoPost {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private final String CREATE_POST = "insert into post_db(owner_id, comment, date) values (?,?,now()) returning id";

    //language=SQL
    private final String ADD_COMMENT_TO_POST = "insert into post_comment_db values(?,?)";

    //language=SQL
    private final String SELECT_POST = "select u_db.id as user_id, u_db.username, u_db.avatar, p_db.date, p_db.comment, p_db.id as post_id from post_db p_db " +
            "left join user_db u_db on u_db.id = p_db.owner_id " +
            "where u_db.id = ? " +
            "order by p_db.date offset ?";

    //language=SQL
    private final String SELECT_IMAGE = "select * from image_db where post_id = ?";


    //language=SQL
    private final String SELECT_POST_PREVIEW = "select distinct on(p_db.id)p_db.id as post_id, i_db.src, i_db.id as image_id, i_db.type from image_db i_db " +
            "left join post_db p_db on p_db.id = i_db.post_id " +
            "where p_db.owner_id = ? " +
            "limit ? " +
            "offset ?";


    //language=SQL
    private final String SELECT_COUNT_LIKES_BY_POST = "select count(*) from like_post_db where post_id = ?";

    //language=SQL
    private final String SELECT_COUNT_COMMENTS_BY_POST = "select count(*) from post_comment_db where post_id = ?";

    //language=SQL
    private final String SELECT_POST_BY_ID = "select p_db.id as post_id, u_db.id as user_id, u_db.username,u_db.avatar, p_db.date, p_db.comment, i_db.src, i_db.type from post_db p_db " +
            "left join image_db i_db on i_db.post_id = p_db.id " +
            "left join user_db u_db on u_db.id = p_db.owner_id " +
            "where p_db.id = ?";

    private RowMapper<Long> ROW_MAPPER_COUNT = (ResultSet resultSet, int rowNum) -> {
        return resultSet.getLong("count");
    };


    private RowMapper<Long> ROW_MAPPER_LONG = (ResultSet resultSet, int rowNum) -> {
        return resultSet.getLong("id");
    };

    private RowMapper<Post> ROW_MAPPER_POST = (ResultSet resultSet, int rowNum) -> {
        User usr = User.builder()
                .id(resultSet.getLong("user_id"))
                .username(resultSet.getString("username"))
                .avatar(resultSet.getString("avatar"))
                .build();
        return Post.builder()
                .id(resultSet.getLong("post_id"))
                .comment(resultSet.getString("comment"))
                .owner(usr)
                .date(resultSet.getDate("date"))
                .build();
    };

    private RowMapper<Image> ROW_MAPPER_IMAGE = (ResultSet resultSet, int rowNum) -> {
        return Image.builder()
                .type(resultSet.getString("type"))
                .src(resultSet.getString("src"))
                .build();
    };

    private RowMapper<Image> ROW_MAPPER_TO_IMAGES = (ResultSet resultSet, int rowNum) -> {
        return Image.builder()
                .type(resultSet.getString("type"))
                .postId(resultSet.getLong("post_id"))
                .src(resultSet.getString("src"))
                .build();
    };

    @Override
    public Long createPost(Post post) {
        return jdbcTemplate.query(CREATE_POST, ROW_MAPPER_LONG, post.getOwner().getId(), post.getComment()).get(0);
    }

    @Override
    public List<Post> getPosts(Long userId, Long postOffset) {
        List<Post> posts = jdbcTemplate.query(SELECT_POST, ROW_MAPPER_POST, userId, postOffset);
        posts.forEach(post -> {
            List<Image> images = jdbcTemplate.query(SELECT_IMAGE, ROW_MAPPER_IMAGE, post.getId());
            post.setImages(new ArrayList<>());
            images.forEach(image -> {
                post.getImages().add(image);
            });
        });
        return posts;
    }

    @Override
    public void addCommentToPost(Long commentId, Long postId) {
        jdbcTemplate.update(ADD_COMMENT_TO_POST, postId, commentId);
    }

    @Override
    public List<DtoPostPreview> getDtoPostsPreview(Long userId, Long postOffset, Long count) {
        List<DtoPostPreview> dtoPostPreviews = new ArrayList<>();
        List<Image> images = jdbcTemplate.query(SELECT_POST_PREVIEW, ROW_MAPPER_TO_IMAGES, userId, count, postOffset);
        images.forEach(img -> {
            Long countLikes = jdbcTemplate.query(SELECT_COUNT_LIKES_BY_POST, ROW_MAPPER_COUNT, img.getPostId()).get(0);
            Long countComments = jdbcTemplate.query(SELECT_COUNT_COMMENTS_BY_POST, ROW_MAPPER_COUNT, img.getPostId()).get(0);
            DtoPostPreview dtoPostPreview = DtoPostPreview.builder()
                    .countComments(countComments)
                    .countLikes(countLikes)
                    .image(img)
                    .build();
            dtoPostPreviews.add(dtoPostPreview);
        });
        return dtoPostPreviews;
    }

    public Post getPostById(Long postId) {
        Post post = jdbcTemplate.query(SELECT_POST_BY_ID, ROW_MAPPER_POST, postId).get(0);
        List<Image> images = jdbcTemplate.query(SELECT_IMAGE, ROW_MAPPER_IMAGE, post.getId());
        post.setImages(images);
        return post;
    }

}
