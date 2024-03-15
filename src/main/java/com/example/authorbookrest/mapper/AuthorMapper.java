package com.example.authorbookrest.mapper;

import com.example.authorbookrest.dto.AuthorResponseDto;
import com.example.authorbookrest.dto.SaveAuthorDto;
import com.example.authorbookrest.entity.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorResponseDto map(Author author);

    Author map(SaveAuthorDto saveAuthorDto);
}
