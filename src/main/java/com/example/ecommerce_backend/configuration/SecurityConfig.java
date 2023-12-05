package com.example.ecommerce_backend.configuration;

import com.example.ecommerce_backend.exceptions.DataNotFoundException;
import com.example.ecommerce_backend.models.User;
import com.example.ecommerce_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;
    @Bean
    public UserDetailsService userDetailsService(){

    }
}
