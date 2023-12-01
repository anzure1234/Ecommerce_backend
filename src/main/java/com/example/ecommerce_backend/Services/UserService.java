package com.example.ecommerce_backend.Services;

import com.example.ecommerce_backend.Models.User;

public interface UserService {
    User createUser (User user);

    String login(String email, String password);

}
