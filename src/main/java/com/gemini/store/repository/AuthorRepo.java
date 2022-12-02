package com.gemini.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gemini.store.entities.Author;

import java.util.Optional;

public interface AuthorRepo extends JpaRepository<Author, Integer> {

    Optional<Author> findByAuthorName(String authorName);

}
