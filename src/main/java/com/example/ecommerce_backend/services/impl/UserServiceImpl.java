package com.example.ecommerce_backend.services.impl;

import com.example.ecommerce_backend.component.JwtTokenUtil;
import com.example.ecommerce_backend.exceptions.DataNotFoundException;
import com.example.ecommerce_backend.models.User;
import com.example.ecommerce_backend.repositories.UserRepository;
import com.example.ecommerce_backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    @Override
    public User createUser(User user) {
        String email = user.getEmail();
        if(userRepository.existsByEmail(email)){
           throw new DataIntegrityViolationException("Email already exits");
        }
        if(user.getFacebookAccountId()==0 &&user.getGoogleAccountId()==0){
            String password = user.getPassword();
           String encodePassword = passwordEncoder.encode(password);
            user.setPassword(encodePassword);
        }
        return userRepository.save(user);
    }



    @Override
    public String login(String email, String password) throws DataNotFoundException {
        Optional<User> optionalUser=userRepository.findByEmail(email);
        if (optionalUser.isEmpty()){
            throw new DataNotFoundException("Invalid user/password");
        }
        User user =optionalUser.get();
        //check password
        if(user.getFacebookAccountId()==0 &&user.getGoogleAccountId()==0) {
            if(!passwordEncoder.matches(password, user.getPassword())){
                throw new BadCredentialsException("Wrong email or password!");
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,password);
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(user);
    }
}
