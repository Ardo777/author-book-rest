package com.example.authorbookrest.endpoint;

import com.example.authorbookrest.dto.AuthorResponseDto;
import com.example.authorbookrest.dto.SaveAuthorDto;
import com.example.authorbookrest.entity.Author;
import com.example.authorbookrest.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/authors")
public class AuthorEndpoint {

    private final AuthorService authorService;

    @PostMapping()
    public AuthorResponseDto createAuthor(@RequestBody SaveAuthorDto saveAuthorDto) {
        return authorService.create(saveAuthorDto);
    }

    @GetMapping
    public List<AuthorResponseDto> getAllAuthors() {
        return authorService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> getById(@PathVariable("id") int id) {
        AuthorResponseDto author = authorService.getById(id);
        if (author == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> update(@PathVariable("id") int id, @RequestBody SaveAuthorDto saveAuthorDto) {
        AuthorResponseDto byId = authorService.getById(id);
        if (byId == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(authorService.updateById(id, saveAuthorDto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> delete(@PathVariable("id") int id) {
        AuthorResponseDto byId = authorService.getById(id);
        if (byId == null) {
            return ResponseEntity.notFound().build();
        }
        authorService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
