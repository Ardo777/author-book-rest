package com.example.authorbookrest.service;

import com.example.authorbookrest.dto.AuthorResponseDto;
import com.example.authorbookrest.dto.SaveAuthorDto;
import com.example.authorbookrest.entity.Author;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthorService {
    AuthorResponseDto create(SaveAuthorDto saveAuthorDto);

    List<AuthorResponseDto> getAll();

    AuthorResponseDto getById(int id);

    AuthorResponseDto updateById(int id,SaveAuthorDto saveAuthorDto);

    void deleteById(int id);
}
