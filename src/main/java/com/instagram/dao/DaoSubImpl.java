package com.instagram.dao;

import com.instagram.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class DaoSubImpl implements DaoSub {

    @Autowired
    private DaoUser daoUser;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private final String INSERT_INTO_SUBS_DB = "insert into subs_db values(?,?)";

    //language=SQL
    private final String DELETE_FROM_SUBS_DB = "delete from subs_db where subscription_id = ? and subscriber_id = ?";

    //language=SQL
    private final String SELECT_COUNT_FROM_SUBS_DB = "select count(*) from subs_db where subscription_id = ? and subscriber_id = ?";

    //language=SQL
    private final String SELECT_FOLLOWERS_ID_BY_FOLLOW_ID = "select subscriber_id from subs_db where subscription_id = ? offset ? limit ?";

    //language=SQL
    private final String SELECT_FOLLOWS_ID_BY_FOLLOWER_ID = "select subscription_id from subs_db where subscriber_id = ? offset ? limit ?";

    //language=SQL
    private String SELECT_COUNT_FOLLOWS = " select count(*) from subs_db where subscriber_id = ?;";

    //language=SQL
    private String SELECT_COUNT_FOLLOWERS = "select count(*) from subs_db where subscription_id = ?;";

    private RowMapper<Integer> ROW_MAPPER_TO_BOOLEAN = (ResultSet resultSet, int rowNum) -> {
        System.out.println("rowmap");
        int i = resultSet.getInt("count");
        System.out.println(i);
        return i;
    };

    private RowMapper<Long> ROW_MAPPER_FOLLOWER_ID = (ResultSet resultSet, int rowNum) -> {
        return resultSet.getLong("subscriber_id");
    };

    private RowMapper<Long> ROW_MAPPER_FOLLOW_ID = (ResultSet resultSet, int rowNum) -> {
        return resultSet.getLong("subscription_id");
    };

    @Override
    public void followToUserByUsername(String usernameSubscription, String usernameSubscriber) {
        User subscriber = daoUser.getUserByUsername(usernameSubscriber);
        User subscription = daoUser.getUserByUsername(usernameSubscription);
        jdbcTemplate.update(INSERT_INTO_SUBS_DB, subscription.getId(), subscriber.getId());
    }

    @Override
    public void unfollowUserByUsername(String usernameSubscription, String usernameSubscriber) {
        User subscriber = daoUser.getUserByUsername(usernameSubscriber);
        User subscription = daoUser.getUserByUsername(usernameSubscription);
        jdbcTemplate.update(DELETE_FROM_SUBS_DB, subscription.getId(), subscriber.getId());
    }

    @Override
    public boolean isFollower(String usernameSubscription, String usernameSubscriber) {
        User subscriber = daoUser.getUserByUsername(usernameSubscriber);
        User subscription = daoUser.getUserByUsername(usernameSubscription);
        int count = jdbcTemplate.query(SELECT_COUNT_FROM_SUBS_DB, ROW_MAPPER_TO_BOOLEAN, subscriber.getId(), subscription.getId()).get(0);
        return count == 1;
    }

    @Override
    public List<User> findFollowersByFollowId(Long followId, int page) {
        int offset = page * 12;
        final int limit = 12;
        List<Long> subscribersId = jdbcTemplate.query(SELECT_FOLLOWERS_ID_BY_FOLLOW_ID, ROW_MAPPER_FOLLOWER_ID, followId, offset, limit);
        List<User> users = new ArrayList<>();
        subscribersId.forEach((elem) -> {
            users.add(daoUser.getUserById(elem));
        });
        return users;
    }

    @Override
    public List<User> findFollowsByFollowerId(Long followerId, int page) {
        int offset = page * 12;
        final int limit = 12;
        List<Long> subscribersId = jdbcTemplate.query(SELECT_FOLLOWS_ID_BY_FOLLOWER_ID, ROW_MAPPER_FOLLOW_ID, followerId, offset, limit);
        List<User> users = new ArrayList<>();
        subscribersId.forEach((elem) -> {
            users.add(daoUser.getUserById(elem));
        });
        return users;
    }

    private RowMapper<Integer> ROW_MAPPER_TO_COUNT = (ResultSet resultSet, int rowNum) -> {
        return resultSet.getInt("count");
    };

    @Override
    public int getCountFollowers(Long userId) {
        return jdbcTemplate.query(SELECT_COUNT_FOLLOWERS, ROW_MAPPER_TO_COUNT, userId).get(0);
    }

    @Override
    public int getCountFollows(Long userId) {
        return jdbcTemplate.query(SELECT_COUNT_FOLLOWS, ROW_MAPPER_TO_COUNT, userId).get(0);
    }
}
