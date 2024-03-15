package com.example.authorbookrest.repository;

import com.example.authorbookrest.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AuthorRepository extends JpaRepository<Author, Integer> {


}
