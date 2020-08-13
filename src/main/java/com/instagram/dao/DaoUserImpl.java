package com.instagram.dao;

import com.instagram.model.Role;
import com.instagram.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class DaoUserImpl implements DaoUser {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private final String SELECT_USER_WITHOUT_ROLE_BY_USERNAME = "select * from user_db where username = ?";

    //language=SQL
    private final String SELECT_USER_WITHOUT_ROLE_BY_ID = "select * from user_db where id = ?";

    //language=SQL
    private final String SELECT_USER_WITH_ROLE_BY_USERNAME = "select u_db.*, r_db.title, r_db.id as role_id from user_db u_db " +
            "left join user_role_db u_r_db on u_r_db.user_id = u_db.id " +
            "left join role_db r_db on u_r_db.role_id = r_db.id " +
            "where u_db.username = ?";


    private RowMapper<User> ROW_MAPPER_TO_USER_WITHOUT_ROLE = (ResultSet resultSet, int rawNum) -> {
        return User.builder()
                .id(resultSet.getLong("id"))
                .password(resultSet.getString("password"))
                .name(resultSet.getString("name"))
                .username(resultSet.getString("username"))
                .mail(resultSet.getString("mail"))
                .website(resultSet.getString("website"))
                .avatar(resultSet.getString("avatar"))
                .description(resultSet.getString("description"))
                .number(resultSet.getString("number"))
                .gender(resultSet.getString("gender"))
                .enabled(resultSet.getBoolean("enabled"))
                .build();
    };
    private RowMapper<User> ROW_MAPPER_TO_USER_WITH_ROLE = (ResultSet resultSet, int rawNum) -> {

        Role role = Role.builder()
                .id(resultSet.getLong("role_id"))
                .title(resultSet.getString("title"))
                .build();

        return User.builder()
                .id(resultSet.getLong("id"))
                .password(resultSet.getString("password"))
                .name(resultSet.getString("name"))
                .username(resultSet.getString("username"))
                .mail(resultSet.getString("mail"))
                .website(resultSet.getString("website"))
                .avatar(resultSet.getString("avatar"))
                .description(resultSet.getString("description"))
                .number(resultSet.getString("number"))
                .gender(resultSet.getString("gender"))
                .enabled(resultSet.getBoolean("enabled"))
                .role(role)
                .build();
    };

    @Override
    public User getUserByUsername(String username) {
        List<User> userList = jdbcTemplate.query(SELECT_USER_WITHOUT_ROLE_BY_USERNAME,ROW_MAPPER_TO_USER_WITHOUT_ROLE,username);
        if(!userList.isEmpty()) {
            return userList.get(0);
        }
        return null;
    }

    @Override
    public User getUserByUsernameWithRole(String username) {
        List<User> userList = jdbcTemplate.query(SELECT_USER_WITH_ROLE_BY_USERNAME,ROW_MAPPER_TO_USER_WITH_ROLE,username);
        if(!userList.isEmpty()) {
            return userList.get(0);
        }
        return null;
    }

    @Override
    public User getUserById(Long id) {
        List<User> userList = jdbcTemplate.query(SELECT_USER_WITHOUT_ROLE_BY_ID,ROW_MAPPER_TO_USER_WITHOUT_ROLE,id);
        if(!userList.isEmpty()) {
            return userList.get(0);
        }
        return null;
    }

    private RowMapper<Long> ROW_MAPPER_LIST_LONG = (ResultSet resultSet, int rowNum) -> {
        return resultSet.getLong("user_id");
    };


    //language=SQL
    private final String SELECT_OPPONENT_USER_IDS_BY_CHAT_IDS = "select user_id from user_chat_db " +
            "where chat_id=? and user_id<>?";

    @Override
    public List<Long> getOpponentUserIdsByChatIds(List<Long> chatIds, Long userId) {
        List<Long> userIds = new ArrayList<>();
        chatIds.forEach(chatId -> {
            userIds.add(jdbcTemplate.query(SELECT_OPPONENT_USER_IDS_BY_CHAT_IDS,ROW_MAPPER_LIST_LONG,chatId,userId).get(0));
        });
        return userIds;
    }

    //language=SQL
    private final String INSERT_USER_INTO_USER_DB = "insert into user_db (password, " +
            "username, mail, enabled, name, birthday) values (?,?,?,?,?,?);";

    //language=SQL
    private final String GET_USER_ID_BY_USERNAME = "select id from user_db where username = ?";

    private RowMapper<Long> ROW_MAPPER_TO_LONG = (ResultSet resultSet, int rowNum) -> {
      return resultSet.getLong("id");
    };

    private Long getUserIdByUsername(String username) {
       return jdbcTemplate.query(GET_USER_ID_BY_USERNAME,ROW_MAPPER_TO_LONG,username).get(0);
    }

    //language=SQL
    private final String INSERT_INTO_USER_ROLE_DB = "insert into user_role_db values (?,?)";

    @Override
    public void createUser(User user) {
        jdbcTemplate.update(INSERT_USER_INTO_USER_DB, user.getPassword(),user.getUsername(),
                user.getMail(),user.isEnabled(),user.getName(),user.getBirthday());
        Long userId = getUserIdByUsername(user.getUsername());
        jdbcTemplate.update(INSERT_INTO_USER_ROLE_DB,userId, 1);
    }
}
