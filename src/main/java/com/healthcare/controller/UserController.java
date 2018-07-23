package com.healthcare.controller;

import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.dao.UserRepository;
import com.healthcare.domain.User;

@RestController
@RequestMapping("/user")
public class UserController {
    
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public UserController(UserRepository userRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    @PostMapping("/sign-up")
    public void signUp(@RequestParam("email") String email, @RequestParam("password") String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setCreatedDate(new Date());
        userRepository.save(user);
        
    }

}
