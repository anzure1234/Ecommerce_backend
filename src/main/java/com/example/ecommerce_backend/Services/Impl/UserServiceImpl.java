package com.example.ecommerce_backend.Services.Impl;

import com.example.ecommerce_backend.Models.User;
import com.example.ecommerce_backend.Repositories.UserRepository;
import com.example.ecommerce_backend.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User createUser(User user) {
        String email = user.getEmail();
        if(userRepository.existsByEmail(email)){
           throw new DataIntegrityViolationException("Email already exits");
        }
        return userRepository.save(user);
    }



    @Override
    public String login(String email, String password) {
        return null;
    }
}
