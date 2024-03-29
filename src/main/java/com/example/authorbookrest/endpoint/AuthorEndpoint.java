package com.example.authorbookrest.endpoint;

import com.example.authorbookrest.dto.AuthorResponseDto;
import com.example.authorbookrest.dto.PagingResponseDto;
import com.example.authorbookrest.dto.SaveAuthorDto;
import com.example.authorbookrest.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public PagingResponseDto getAllAuthors(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @RequestParam(value = "orderBy", required = false, defaultValue = "id") String orderBy,
            @RequestParam(value = "order", required = false, defaultValue = "DESC") String order) {

        Sort sort = Sort.by(Sort.Direction.fromString(order), orderBy);
        Pageable pageable = PageRequest.of(page - 1
                , size, sort);
        return authorService.getAll(pageable);
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
