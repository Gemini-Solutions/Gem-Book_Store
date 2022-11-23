package com.gemini.store.service.impl;

import java.util.List;
import java.util.Locale.Category;
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
class BookServiceImpl implements BookService{

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
		log.info("BookServiceImpl: addBook started for bookTitle {}",bookDto.getBooktitle());
		try {
		Book book=new Book();
		Author author=null;
		Publisher publisher=null;
		author=authorRepo.findByAuthorName(bookDto.getAuthorName());
		publisher=publisherRepo.findByPublisherName(bookDto.getPublisherName());
		if(author==null) {
			author=new Author();
		author.setAuthorName(bookDto.getAuthorName());
		author.setAuthorNationality(bookDto.getAuthorNationality());
		}
		if(publisher==null) {
			 publisher=new Publisher();
		publisher.setPublisherName(bookDto.getAuthorName());
		publisher.setPublisherNationality(bookDto.getAuthorNationality());
		}
		
		book.setBooktitle(bookDto.getBooktitle());
		book.setEdition(bookDto.getEdition());
		book.setPublishdate(bookDto.getPublishdate());
		book.setAuthorId(author);
		book.setPublisherId(publisher);
		bookRepo.save(book);
		}
		catch(Exception ex)
		{			
			log.error("BookServiceImpl: error occured while calling addbook {}",ex.getMessage());
		}
		return bookDto;
	}

	@Override
	public BookDto updateBook(BookDto bookDto, Integer bookId) {
		log.info("BookServiceImpl: updateBook started for bookTitle {}",bookDto.getBooktitle());
		Book book=this.bookRepo.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book","Book Id", bookId));
		Author author=new Author();
		Publisher publisher=new Publisher();
		author=authorRepo.findByAuthorName(bookDto.getAuthorName());
		publisher=publisherRepo.findByPublisherName(bookDto.getPublisherName());
		if(author==null) {
		author.setAuthorName(bookDto.getAuthorName());
		author.setAuthorNationality(bookDto.getAuthorNationality());
		}
		if(publisher==null) {
		publisher.setPublisherName(bookDto.getAuthorName());
		publisher.setPublisherNationality(bookDto.getAuthorNationality());
		}
		
		book.setBooktitle(bookDto.getBooktitle());
		book.setEdition(bookDto.getEdition());
		book.setPublishdate(bookDto.getPublishdate());
		book.setAuthorId(author);
		book.setPublisherId(publisher);
		bookRepo.save(book);
		log.info("BookServiceImpl:details updated successfully for bookTitle {}",bookDto.getBooktitle());
		return bookDto;
	}

	@Override
	public void deleteBook(Integer bookId) {
		// TODO Auto-generated method stub
		log.info("BookServiceImpl: deleteBook started for bookId {}",bookId);
		Book book=this.bookRepo.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book","Book Id", bookId));
		publisherRepo.delete(book.getPublisherId());
		authorRepo.delete(book.getAuthorId());
		bookRepo.delete(book);
		log.info("BookServiceImpl:details deleted successfully for bookId {}",bookId);
	}

	@Override
	public BookDto getBook(Integer bookId) {
		// TODO Auto-generated method stub
		log.info("BookServiceImpl: getBook started for bookId {}",bookId);
		Book book=this.bookRepo.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book","Book Id", bookId));
		BookDto bookDto=new BookDto();
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
		List<Book> books=this.bookRepo.findAll();
		List<BookDto> bookDtoList=books.stream().map(book->{
			BookDto bookDto=new BookDto();
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
	}

}
