package com.example.authorbookrest.service.impl;

import com.example.authorbookrest.dto.SaveUserDto;
import com.example.authorbookrest.dto.UserResponseDto;
import com.example.authorbookrest.entity.User;
import com.example.authorbookrest.mapper.UserMapper;
import com.example.authorbookrest.repository.UserRepository;
import com.example.authorbookrest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public SaveUserDto create(UserResponseDto userResponseDto) {
        User user= userMapper.map(userResponseDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userMapper.map(userRepository.save(user));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
