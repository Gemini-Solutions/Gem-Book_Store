package com.gemini.store.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gemini.store.entities.Author;
import com.gemini.store.entities.Book;
import com.gemini.store.entities.Publisher;
import com.gemini.store.payloads.BookDto;
import com.gemini.store.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class BookControllerTest {

    @Mock
    BookService bookService;
    @Autowired
    MockMvc mockMvc;
    @InjectMocks
    BookController bookController;
    BookDto bookDto = null;
    Author author = null;
    Publisher publisher = null;
    Book book = null;
    List<Book> books = null;
    private ObjectMapper mapper = new ObjectMapper();
    List<BookDto> booksDto = null;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
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
        BookDto bookDto1 = new BookDto("test1", "test2", "test3", "test4", "test5", "test6", "test7");
        booksDto = Arrays.asList(bookDto, bookDto1);
    }

    @Test
    void addBook() throws Exception {
        String jsonBody = mapper.writeValueAsString(bookDto);
        when(bookService.addBook(bookDto)).thenReturn(bookDto);
        this.mockMvc
                .perform(post("/api/bookstore").content(jsonBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void updateBook() throws Exception {
        Integer bookId = 123;
        String jsonBody = mapper.writeValueAsString(bookDto);
        when(bookService.updateBook(bookDto, bookId)).thenReturn(bookDto);
        this.mockMvc
                .perform(put("/api/bookstore/{bookId}", bookId).content(jsonBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getBooks() throws Exception {
        when(bookService.getBooks()).thenReturn(booksDto);
//        String jsonBody = mapper.writeValueAsString(bookDto);
        mockMvc.perform(get("/api/bookstore").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void getBook() throws Exception {
        Integer bookId = 123;
        when(bookService.getBook(bookId)).thenReturn(bookDto);
        mockMvc.perform(get("/api/bookstore/{bookId}", bookId)).andExpect(status().isOk());
    }

    @Test
    void deleteBook() throws Exception {
        Integer bookId = 123;
        mockMvc.perform(delete("/api/bookstore/{bookId}", bookId)).andExpect(status().isOk());
    }
}