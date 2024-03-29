package com.example.authorbookrest.service.impl;

import com.example.authorbookrest.dto.SaveUserDto;
import com.example.authorbookrest.dto.UserResponseDto;
import com.example.authorbookrest.entity.User;
import com.example.authorbookrest.mapper.UserMapper;
import com.example.authorbookrest.repository.UserRepository;
import com.example.authorbookrest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Value("${upload.image.path}")
    private String uploadImagePath;

    @Override
    public SaveUserDto create(UserResponseDto userResponseDto) {
        User user = userMapper.map(userResponseDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.map(userRepository.save(user));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void uploadImage(User user, MultipartFile multipartFile) throws IOException {
        if (multipartFile !=null && !multipartFile.isEmpty()){
            String fileName=System.currentTimeMillis()+"_"+multipartFile.getOriginalFilename();
            multipartFile.transferTo(new File(uploadImagePath,fileName));
            user.setImagePath(fileName);
            userRepository.save(user);
        }
    }
}
