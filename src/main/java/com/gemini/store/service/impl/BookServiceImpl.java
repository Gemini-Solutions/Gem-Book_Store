package com.gemini.store.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemini.store.entities.Author;
import com.gemini.store.entities.Book;
import com.gemini.store.entities.Publisher;
import com.gemini.store.exceptions.ResourceNotFoundException;
import com.gemini.store.payloads.BookDto;
import com.gemini.store.repository.AuthorRepo;
import com.gemini.store.repository.BookRepo;
import com.gemini.store.repository.PublisherRepo;
import com.gemini.store.service.BookService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
class BookServiceImpl implements BookService {

    @Autowired
    BookRepo bookRepo;

    @Autowired
    AuthorRepo authorRepo;

    @Autowired
    PublisherRepo publisherRepo;

    public BookServiceImpl(BookRepo bookRepo, AuthorRepo authorRepo, PublisherRepo publisherRepo) {
        super();
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
        this.publisherRepo = publisherRepo;
    }

    @Override
    public BookDto addBook(BookDto bookDto) {
        log.info("BookServiceImpl: addBook started for bookTitle {}", bookDto.getBooktitle());
        try {
            Book book = new Book();
            Author author = null;
            Optional<Publisher> publisher = null;
            Optional<Author> authorOptional = null;
            authorOptional = authorRepo.findByAuthorName(bookDto.getAuthorName());
            if (authorOptional.isPresent()) {
                author = authorOptional.get();
            }
            else{
                author = new Author();
                author.setAuthorName(bookDto.getAuthorName());
                author.setAuthorNationality(bookDto.getAuthorNationality());
            }
            publisher = publisherRepo.findByPublisherName(bookDto.getPublisherName());
            Publisher publisher1 = null;
            if (publisher.isPresent()) {
                publisher1 = publisher.get();
            } else {
                publisher1 = new Publisher();
                publisher1.setPublisherName(bookDto.getPublisherName());
                publisher1.setPublisherNationality(bookDto.getPublisherNationality());
            }

            book.setBooktitle(bookDto.getBooktitle());
            book.setEdition(bookDto.getEdition());
            book.setPublishdate(bookDto.getPublishdate());
            book.setAuthorId(author);
            book.setPublisherId(publisher1);
         book=   bookRepo.save(book);
         bookDto.setBookId(book.getBookISIN());
            return bookDto;
        } catch (Exception ex) {
            log.error("BookServiceImpl: error occured while calling addbook {}", ex.getMessage());
            return null;
        }
    }

    @Override
    public BookDto updateBook(BookDto bookDto, Integer bookId) {
        log.info("BookServiceImpl: updateBook started for bookTitle {}", bookDto.getBooktitle());
        try {
            Book book = this.bookRepo.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book", "Book Id", bookId));
            Author author = null;
            Optional<Publisher> publisher = null;
            Optional<Author> authorOptional = null;
            authorOptional = authorRepo.findByAuthorName(bookDto.getAuthorName());
            if (authorOptional.isPresent()) {
                author = authorOptional.get();
            }
            else{
                author = new Author();
                author.setAuthorName(bookDto.getAuthorName());
                author.setAuthorNationality(bookDto.getAuthorNationality());
            }
            publisher = publisherRepo.findByPublisherName(bookDto.getPublisherName());
            Publisher publisher1 = null;
            if (publisher.isPresent()) {
                publisher1 = publisher.get();
            } else {
                publisher1 = new Publisher();
                publisher1.setPublisherName(bookDto.getPublisherName());
                publisher1.setPublisherNationality(bookDto.getPublisherNationality());
            }
            book.setBooktitle(bookDto.getBooktitle());
            book.setEdition(bookDto.getEdition());
            book.setPublishdate(bookDto.getPublishdate());
            book.setAuthorId(author);
            book.setPublisherId(publisher1);
            book=bookRepo.save(book);
            bookDto.setBookId(book.getBookISIN());
            return bookDto;
        } catch (Exception ex) {
            log.error("BookServiceImpl: error occured while calling updateBook {}", ex.getMessage());
            return null;
        }
    }

    @Override
    public void deleteBook(Integer bookId) {
        // TODO Auto-generated method stub
        log.info("BookServiceImpl: deleteBook started for bookId {}", bookId);
    Book book= this.bookRepo.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book", "Book Id", bookId));
        book.setAuthorId(null);
        book.setPublisherId(null);
     bookRepo.delete(book);
    }

    @Override
    public BookDto getBook(Integer bookId) {
        // TODO Auto-generated method stub
        log.info("BookServiceImpl: getBook started for bookId {}", bookId);
        Book book = this.bookRepo.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book", "Book Id", bookId));
        BookDto bookDto = new BookDto();
        bookDto.setBookId(book.getBookISIN());
        bookDto.setAuthorName(book.getAuthorId().getAuthorName());
        bookDto.setAuthorNationality(book.getAuthorId().getAuthorNationality());
        bookDto.setBooktitle(book.getBooktitle());
        bookDto.setEdition(book.getEdition());
        bookDto.setPublishdate(book.getPublishdate());
        bookDto.setPublisherName(book.getPublisherId().getPublisherName());
        bookDto.setPublisherNationality(book.getPublisherId().getPublisherNationality());
        return bookDto;
    }

    @Override
    public List<BookDto> getBooks() {
        log.info("BookServiceImpl: getBooks started");
        try {
            List<Book> books = this.bookRepo.findAll();
            List<BookDto> bookDtoList = books.stream().map(book -> {
                BookDto bookDto = new BookDto();
                bookDto.setBookId(book.getBookISIN());
                bookDto.setAuthorName(book.getAuthorId().getAuthorName());
                bookDto.setAuthorNationality(book.getAuthorId().getAuthorNationality());
                bookDto.setBooktitle(book.getBooktitle());
                bookDto.setEdition(book.getEdition());
                bookDto.setPublishdate(book.getPublishdate());
                bookDto.setPublisherName(book.getPublisherId().getPublisherName());
                bookDto.setPublisherNationality(book.getPublisherId().getPublisherNationality());
                return bookDto;
            }).collect(Collectors.toList());
            return bookDtoList;
        } catch (Exception ex) {
            log.error("BookServiceImpl: error occured while calling getBooks {}", ex.getMessage());
            return null;
        }
    }

}
