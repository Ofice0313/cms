package com.mcts.cms.config;

import com.mcts.cms.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class UserDetailsServiceConfig {

    @Bean
    UserDetailsService userDetailsService(UserService userService) {
        return userService;
    }
}