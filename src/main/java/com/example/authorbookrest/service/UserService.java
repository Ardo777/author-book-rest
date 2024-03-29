package com.example.authorbookrest.service;

import com.example.authorbookrest.dto.SaveUserDto;
import com.example.authorbookrest.dto.UserResponseDto;
import com.example.authorbookrest.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    SaveUserDto create(UserResponseDto userResponseDto);

    User findByEmail(String email );

    User findById(int id);

    void uploadImage(User user, MultipartFile multipartFile) throws IOException;
}
