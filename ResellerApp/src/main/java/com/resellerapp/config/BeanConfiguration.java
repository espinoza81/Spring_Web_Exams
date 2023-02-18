package com.resellerapp.config;

import com.resellerapp.model.helpers.LoggedUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class BeanConfiguration {

    @Bean
    @SessionScope
    public LoggedUser loggedUser() {
        return new LoggedUser();
    }
}
