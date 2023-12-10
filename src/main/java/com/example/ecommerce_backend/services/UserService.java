package com.example.ecommerce_backend.services;

import com.example.ecommerce_backend.exceptions.DataNotFoundException;
import com.example.ecommerce_backend.models.User;

public interface UserService {
    User createUser(User user);

    String login(String email, String password) throws DataNotFoundException;

}
