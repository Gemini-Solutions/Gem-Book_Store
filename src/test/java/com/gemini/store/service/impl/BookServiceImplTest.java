package com.gemini.store.service.impl;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.CollectionUtils;
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
	AuthorRepo authoRepo;
	@Mock
	PublisherRepo publisherRepo;

	BookService bookService;

	@BeforeEach
	public void setUp()
	{
		bookService=new BookServiceImpl(bookRepo,authoRepo,publisherRepo);
	}
	
	@Test
	void testAddBook() {
		BookDto bookDto=new BookDto("test1","test2","test3","test4","test5","test6","test7");
		BookDto bookDto2=bookService.addBook(bookDto);
		assertTrue(!ObjectUtils.isEmpty(bookDto2));
	}

	@Test
	void testUpdateBook() {
		Integer bookId=1;
		BookDto bookDto=new BookDto("test1","test2","test3","test4","test5","test6","test7");
	       Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
	    	   bookService.updateBook(bookDto,bookId);
	        });
	        Assertions.assertTrue(exception.getMessage().contains("not found with"));
		assertThatExceptionOfType(ResourceNotFoundException.class);
	}

	@Test
	void testDeleteBook() {
		Integer bookId=1;
	       Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
	    	   bookService.deleteBook(bookId);
	        });
	        Assertions.assertTrue(exception.getMessage().contains("not found with"));
		assertThatExceptionOfType(ResourceNotFoundException.class);
	}

	@Test
	void testGetBook() {
		Integer bookId=1;
	       Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
	    	   bookService.getBook(bookId);
	        });
	        Assertions.assertTrue(exception.getMessage().contains("not found with"));
		assertThatExceptionOfType(ResourceNotFoundException.class);
	}

	@Test
	void testGetBooks() {
		List<BookDto> books=bookService.getBooks();
		assertTrue(org.springframework.util.CollectionUtils.isEmpty(books));
	}

}
