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

    private RowMapper<Integer> ROW_MAPPER_TO_BOOLEAN = (ResultSet resultSet, int rowNum) -> {
        System.out.println("rowmap");
        int i = resultSet.getInt("count");
        System.out.println(i);
        return i;
    };

    @Override
    public void followToUserByUsername(String usernameSubscription, String usernameSubscriber) {
        User subscriber = daoUser.findUserWithoutRoleByUsername(usernameSubscriber);
        User subscription = daoUser.findUserWithoutRoleByUsername(usernameSubscription);
        jdbcTemplate.update(INSERT_INTO_SUBS_DB, subscription.getId(), subscriber.getId());
    }

    @Override
    public void unfollowUserByUsername(String usernameSubscription, String usernameSubscriber) {
        User subscriber = daoUser.findUserWithoutRoleByUsername(usernameSubscriber);
        User subscription = daoUser.findUserWithoutRoleByUsername(usernameSubscription);
        jdbcTemplate.update(DELETE_FROM_SUBS_DB, subscription.getId(), subscriber.getId());
    }

    @Override
    public boolean isFollower(String usernameSubscription, String usernameSubscriber) {
        User subscriber = daoUser.findUserWithoutRoleByUsername(usernameSubscriber);
        User subscription = daoUser.findUserWithoutRoleByUsername(usernameSubscription);
        System.out.println(subscription.getId());
        System.out.println(subscriber.getId());
        int count = jdbcTemplate.query(SELECT_COUNT_FROM_SUBS_DB, ROW_MAPPER_TO_BOOLEAN, subscriber.getId(), subscription.getId()).get(0);
        return count == 1;
    }


    //language=SQL
    private final String SELECT_FOLLOWERS_ID_BY_FOLLOW_ID = "select subscriber_id from subs_db where subscription_id = ? offset ? limit ?";

    //language=SQL
    private final String SELECT_FOLLOWS_ID_BY_FOLLOWER_ID = "select subscription_id from subs_db where subscriber_id = ? offset ? limit ?";

    private RowMapper<Long> ROW_MAPPER_FOLLOWER_ID = (ResultSet resultSet, int rowNum) -> {

        return resultSet.getLong("subscriber_id");
//        return User.builder()
//                .id(resultSet.getLong("id"))
//                .enabled(resultSet.getBoolean("enabled"))
//                .birthday(resultSet.getDate("birthday"))
//                .mail(resultSet.getString("mail"))
//                .password(resultSet.getString("password"))
//                .name(resultSet.getString("name"))
//                .username(resultSet.getString("username"))
//                .gender(resultSet.getString("gender"))
//                .number(resultSet.getString("number"))
//                .website(resultSet.getString("website"))
//                .avatar(resultSet.getString("avatar"))
//                .description(resultSet.getString("description"))
//                .build();
    };

    private RowMapper<Long> ROW_MAPPER_FOLLOW_ID = (ResultSet resultSet, int rowNum) -> {
        return resultSet.getLong("subscription_id");
    };

    @Override
    public List<User> findFollowersByFollowId(Long followId, int page) {
        int offset = page * 12;
        final int limit = 12;
        List<Long> subscribersId = jdbcTemplate.query(SELECT_FOLLOWERS_ID_BY_FOLLOW_ID, ROW_MAPPER_FOLLOWER_ID, followId, offset, limit);
        List<User> users = new ArrayList<>();
        subscribersId.forEach((elem)->{
            users.add(daoUser.findUserWithoutRoleById(elem));
        });
        return users;
    }

    @Override
    public List<User> findFollowsByFollowerId(Long followerId, int page) {
        int offset = page * 12;
        final int limit = 12;
        List<Long> subscribersId = jdbcTemplate.query(SELECT_FOLLOWS_ID_BY_FOLLOWER_ID, ROW_MAPPER_FOLLOW_ID, followerId, offset, limit);
        List<User> users = new ArrayList<>();
        subscribersId.forEach((elem)->{
            users.add(daoUser.findUserWithoutRoleById(elem));
        });
        System.out.println("users = " + users.size());
        return users;
    }
}
