package com.gemini.store.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gemini.store.payloads.ApiResponse;
import com.gemini.store.payloads.BookDto;
import com.gemini.store.service.BookService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/bookstore")
@Slf4j
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("")
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        log.info("BookController: In AddBook");
        BookDto addBookDto = bookService.addBook(bookDto);
        return new ResponseEntity<>(addBookDto, HttpStatus.CREATED);
    }


    @PutMapping("/{bookId}")
    public ResponseEntity<ApiResponse> updateBook(@RequestBody BookDto BookDto, @PathVariable("bookId") Integer bookId) {
        log.info("BookController: In updateBook");
        BookDto updatedBook = bookService.updateBook(BookDto, bookId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Book updated successfully", "200", "Success"), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<BookDto>> getBooks() {
        log.info("BookController: In getBooks");
        return new ResponseEntity(bookService.getBooks(), HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto> getBook(@PathVariable Integer bookId) {
        log.info("BookController: In getBook");
        return new ResponseEntity(bookService.getBook(bookId), HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable Integer bookId) {
        log.info("BookController: In deleteBook");
        bookService.deleteBook(bookId);
        return new ResponseEntity(new ApiResponse("Book deleted successfully", "200", "Success"), HttpStatus.OK);
    }


}
