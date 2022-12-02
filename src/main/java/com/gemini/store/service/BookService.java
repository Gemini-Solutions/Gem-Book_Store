package com.gemini.store.service;

import java.util.List;

import com.gemini.store.payloads.BookDto;


public interface BookService {
    BookDto addBook(BookDto bookDto);

    BookDto updateBook(BookDto BookDto, Integer bookId);

    void deleteBook(Integer bookId);

    BookDto getBook(Integer bookId);

    List<BookDto> getBooks();
}
