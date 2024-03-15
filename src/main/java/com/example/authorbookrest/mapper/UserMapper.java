package com.example.authorbookrest.mapper;

import com.example.authorbookrest.dto.SaveUserDto;
import com.example.authorbookrest.dto.UserResponseDto;
import com.example.authorbookrest.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    SaveUserDto map(User user);

    @Mapping(target = "userType",constant = "USER")
    User map(UserResponseDto userResponseDto);
}
