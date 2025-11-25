package com.example.user_login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.user_login.dto.UserDto;
import com.example.user_login.Models.usermodel;
import com.example.user_login.Repository.UserRepository;

@Service
public class UserService{
   
    @Autowired
    private PasswordEncoder passwordEncoder;
   
    @Autowired
    private UserRepository userRepository;

    public usermodel save(UserDto userDto) {
        usermodel user = new usermodel(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()) , userDto.getFullname());
        return userRepository.save(user);
    }
}