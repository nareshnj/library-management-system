package com.nareshnj.lms.service;

import com.nareshnj.lms.entity.Book;
import com.nareshnj.lms.entity.BookDetails;
import com.nareshnj.lms.repository.BookRepository;
import com.nareshnj.lms.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void test_givenBooksNotPresentInLibrary_then_returnEmptyLibrary() {

        List<Book> emptyBookList = Collections.emptyList();
        when(bookRepository.findAll()).thenReturn(emptyBookList);
        List<Book> bookList = bookService.getAll();
        assertBooks(emptyBookList, bookList);
    }

    @Test
    public void test_givenBooksPresentInLibrary_then_returnBookList() {

        List<Book> books = getBookList();
        when(bookRepository.findAll()).thenReturn(books);
        List<Book> bookList = bookService.getAll();
        assertBooks(books, bookList);
    }

    @Test
    public void test_givenBooksPresentInLibrary_the_userCanBorrow() {

        List<Book> books = getBookList();
        when(bookService.getAll()).thenReturn(books);
        List<Book> allBooks = bookService.getAll();
    }

    private void assertBooks(List<Book> expectedBooks, List<Book> actualBooks) {
        assertEquals(expectedBooks.size(), actualBooks.size());
        for (int i = 0; i < expectedBooks.size(); i++) {
            assertEquals(expectedBooks.get(i), actualBooks.get(i));
        }
    }

    private List<Book> getBookList() {
        List<Book> bookList = new ArrayList<>();

        BookDetails bookDetails1 = new BookDetails();
        bookDetails1.setId(1L);
        bookDetails1.setQuantity(3);
        Book book1 = new Book();
        book1.setId(1L);
        book1.setName("Clean Code");
        book1.setAuthor("Robert C. Martin");
        book1.setBookDetails(bookDetails1);
        bookList.add(book1);

        BookDetails bookDetails2 = new BookDetails();
        bookDetails2.setId(2L);
        bookDetails2.setQuantity(5);
        Book book2 = new Book();
        book2.setId(2L);
        book2.setName("Effective Java");
        book2.setAuthor("Joshua Bloch");
        book2.setBookDetails(bookDetails2);
        bookList.add(book2);

        return bookList;
    }
}
