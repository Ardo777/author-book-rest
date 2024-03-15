package com.example.authorbookrest.service;

import com.example.authorbookrest.dto.SaveUserDto;
import com.example.authorbookrest.dto.UserResponseDto;
import com.example.authorbookrest.entity.User;

public interface UserService {

    SaveUserDto create(UserResponseDto userResponseDto);

    User findByEmail(String email );
}
