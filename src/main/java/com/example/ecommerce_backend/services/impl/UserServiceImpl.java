package com.example.ecommerce_backend.services.impl;

import com.example.ecommerce_backend.models.User;
import com.example.ecommerce_backend.repositories.UserRepository;
import com.example.ecommerce_backend.services.UserService;
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
//        if(user.getFacebookAccountId()==0 &&user.getGoogleAccountId()==0){
//            String password = user.getPassword();
//            String encodePassword = passwordEncoder.encode(password);
//            user.setPassword(encodePassword);
//        }
        return userRepository.save(user);
    }



    @Override
    public String login(String email, String password) {
        return null;
    }
}
