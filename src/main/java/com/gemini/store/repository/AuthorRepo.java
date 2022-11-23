package com.gemini.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gemini.store.entities.Author;

public interface AuthorRepo extends JpaRepository<Author, Integer> {
	
	Author findByAuthorName(String authorName);

}
