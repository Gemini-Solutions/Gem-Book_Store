package com.gemini.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gemini.store.entities.Book;

public interface BookRepo extends JpaRepository<Book, Integer> {

}
