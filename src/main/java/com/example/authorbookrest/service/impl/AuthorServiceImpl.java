package com.example.authorbookrest.service.impl;

import com.example.authorbookrest.dto.AuthorResponseDto;
import com.example.authorbookrest.dto.SaveAuthorDto;
import com.example.authorbookrest.entity.Author;
import com.example.authorbookrest.mapper.AuthorMapper;
import com.example.authorbookrest.repository.AuthorRepository;
import com.example.authorbookrest.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public AuthorResponseDto create(SaveAuthorDto saveAuthorDto) {
        return authorMapper.map(authorRepository.save(
                authorMapper.map(saveAuthorDto)));
    }

    @Override
    public List<AuthorResponseDto> getAll() {
        List<Author>all= authorRepository.findAll();
        List<AuthorResponseDto> authorResponseDtoList=new ArrayList<>();
        for (Author author : all) {
            authorResponseDtoList.add(authorMapper.map(author));
        }
        return authorResponseDtoList;
    }

    @Override
    public AuthorResponseDto getById(int id) {
        Author author= authorRepository.findById(id)
                .orElse(null);
        if (author==null) {
        return null;
        }
      return authorMapper.map(author);
    }

    @Override
    public AuthorResponseDto updateById(int id, SaveAuthorDto saveAuthorDto) {
        Author savedAuthor = authorMapper.map(saveAuthorDto);
        savedAuthor.setId(id);
        authorRepository.save(savedAuthor);

        return authorMapper.map(savedAuthor);
    }

    @Override
    public void deleteById(int id) {
        authorRepository.deleteById(id);
    }


}
