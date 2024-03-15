package com.example.authorbookrest.dto;

import com.example.authorbookrest.entity.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveUserDto {

    private int id;

    private String name;

    private String surname;

    private String email;

    private UserType userType;




}
