package com.instagram.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class ConfigurationWebSecurity extends WebSecurityConfigurerAdapter {

    //language=SQL
    private final String USERS_BY_USERNAME_QUERY =
            "select username, password, enabled from user_db where username = ?";

    //language=SQL
    private final String AUTHORITIES_BY_NICKNAME_QUERY =
            "select username, r.title from user_db left join user_role_db on user_role_db.user_id = user_db.id " +
                    "left join role_db r on user_role_db.role_id = r.id where username=?";


    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery(USERS_BY_USERNAME_QUERY)
                .authoritiesByUsernameQuery(AUTHORITIES_BY_NICKNAME_QUERY);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/","/{username}/")
                .hasRole("USER")
                .and()
                .formLogin()
                .loginPage("/accounts/login")
                .usernameParameter("username")
                .failureForwardUrl("/accounts/login")
                .and()
                .logout()
                .logoutSuccessUrl("/accounts/login")
                .and()
                .rememberMe().alwaysRemember(false);
    }

}
