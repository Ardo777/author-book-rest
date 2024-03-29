package com.example.authorbookrest.mapper;

import com.example.authorbookrest.dto.AuthorResponseDto;
import com.example.authorbookrest.dto.BookResponseDto;
import com.example.authorbookrest.dto.SaveAuthorDto;
import com.example.authorbookrest.dto.SaveBookDto;
import com.example.authorbookrest.entity.Author;
import com.example.authorbookrest.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public interface BookMapper {

    @Mapping(target = "authorResponseDto", source = "author")
    BookResponseDto map(Book book);

    Book map(SaveBookDto saveBookDto);
}
