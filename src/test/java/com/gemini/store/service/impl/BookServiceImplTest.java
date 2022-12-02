package com.gemini.store.service.impl;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.gemini.store.entities.Author;
import com.gemini.store.entities.Book;
import com.gemini.store.entities.Publisher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.CollectionUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.ObjectUtils;

import com.gemini.store.exceptions.ResourceNotFoundException;
import com.gemini.store.payloads.BookDto;
import com.gemini.store.repository.AuthorRepo;
import com.gemini.store.repository.BookRepo;
import com.gemini.store.repository.PublisherRepo;
import com.gemini.store.service.BookService;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    BookRepo bookRepo;
    @Mock
    AuthorRepo authorRepo;
    @Mock
    PublisherRepo publisherRepo;

    @InjectMocks
    BookServiceImpl bookService;
    BookDto bookDto = null;
    Author author = null;
    Publisher publisher = null;
    Book book = null;
    List<Book> books = null;

    @BeforeEach
    public void setUp() {

        bookDto = new BookDto("test1", "test2", "test3", "test4", "test5", "test6", "test7");
        author = new Author();
        author.setAuthorNationality("Indian");
        author.setAuthorName(bookDto.getAuthorName());
        author.setAuthorId(1);
        publisher = new Publisher();
        publisher.setPublisherNationality("Indian");
        publisher.setPublisherName(bookDto.getPublisherName());
        publisher.setPublisherId(1);
        book = new Book();
        book.setBookISIN(123);
        book.setAuthorId(author);
        book.setBooktitle(bookDto.getBooktitle());
        book.setPublisherId(publisher);
        book.setEdition(bookDto.getEdition());
        book.setPublishdate(bookDto.getPublishdate());
        Book book1 = new Book();
        book1.setBookISIN(124);
        book1.setAuthorId(author);
        book1.setBooktitle(bookDto.getBooktitle());
        book1.setPublisherId(publisher);
        book1.setEdition(bookDto.getEdition());
        book1.setPublishdate(bookDto.getPublishdate());
        books = Arrays.asList(book, book1);
    }

    @Test
    void testAddBook() {
        when(authorRepo.findByAuthorName(any())).thenReturn(Optional.of(author));
        when(publisherRepo.findByPublisherName(any())).thenReturn(Optional.of(publisher));
        when(bookRepo.save(any())).thenReturn(book);
        BookDto bookDto2 = bookService.addBook(bookDto);
        assertEquals(bookDto2, bookDto);
    }

    @Test
    void testAddBookNegative() {
        when(authorRepo.findByAuthorName(any())).thenReturn(null);
        BookDto bookDto2 = bookService.addBook(bookDto);
        assertTrue(ObjectUtils.isEmpty(bookDto2));
    }

    @Test
    void testUpdateBook() {
        Integer bookId = 123;
        when(bookRepo.findById(any())).thenReturn(Optional.of(book));
        when(authorRepo.findByAuthorName(any())).thenReturn(Optional.of(author));
        when(publisherRepo.findByPublisherName(any())).thenReturn(Optional.of(publisher));
        when(bookRepo.save(any())).thenReturn(book);
        BookDto bookDto2 = bookService.updateBook(bookDto, bookId);
        assertEquals(bookDto2, bookDto);
    }

    @Test
    void testUpdateBookNegative() {
        Integer bookId = 123;
        when(bookRepo.findById(any())).thenReturn(null);
        bookDto = bookService.updateBook(bookDto, bookId);
        assertTrue(ObjectUtils.isEmpty(bookDto));
    }

    @Test
    void testDeleteBook() {
        Integer bookId = 1;
        when(bookRepo.findById(any())).thenReturn(Optional.of(book));
        bookService.deleteBook(bookId);
        verify(bookRepo, times(1)).delete(book);
    }

    @Test
    void testDeleteBookNegative() {
        Integer bookId = 1;
        Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            bookService.deleteBook(bookId);
        });
        Assertions.assertTrue(exception.getMessage().contains("not found with"));
        assertThatExceptionOfType(ResourceNotFoundException.class);
    }

    @Test
    void testGetBook() {
        Integer bookId = 1;
        when(this.bookRepo.findById(any())).thenReturn(Optional.of(book));
        assertEquals(bookService.getBook(bookId).getBooktitle(), bookDto.getBooktitle());
    }

    @Test
    void testGetBookNegative() {
        Integer bookId = 1;
        Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            bookService.getBook(bookId);
        });
        Assertions.assertTrue(exception.getMessage().contains("not found with"));
        assertThatExceptionOfType(ResourceNotFoundException.class);
    }

    @Test
    void testGetBooks() {
        when(this.bookRepo.findAll()).thenReturn(books);
        List<BookDto> booksDto = bookService.getBooks();
        assertEquals(books.size(), booksDto.size());
    }

    @Test
    void testGetBooksNegative() {
        when(this.bookRepo.findAll()).thenReturn(null);
        List<BookDto> booksDto = bookService.getBooks();
        assertTrue(ObjectUtils.isEmpty(booksDto));
    }
}
