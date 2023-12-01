package com.example.ecommerce_backend.Controller;

import com.example.ecommerce_backend.Dtos.UserDto;
import com.example.ecommerce_backend.Dtos.UserLoginDto;
import com.example.ecommerce_backend.Models.User;
import com.example.ecommerce_backend.Services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDto userDto,
            BindingResult result){
        try{
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if(!userDto.getPassword().equals(userDto.getReTypePassword())){
                return ResponseEntity.badRequest().body("Password and confirm password not match");
            }
            User user = new User();
            BeanUtils.copyProperties(userDto,user);
            user.setRole(userDto.getRole());
            userService.createUser(user);
            return ResponseEntity.ok("Register successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDto userLoginDto){
        return ResponseEntity.ok("some token");
    }
}
