package com.nareshnj.lms.data;

import com.nareshnj.lms.entity.Book;
import com.nareshnj.lms.entity.BookDetails;
import com.nareshnj.lms.entity.User;

import java.util.ArrayList;
import java.util.List;

public class LibraryData {
    public static final long BOOK_1_ID = 1L;
    public static final String BOOK_1_NAME = "Clean Code";
    public static final String BOOK_1_AUTHOR = "Robert C. Martin";
    public static final long BOOK_1_DETAILS_ID = 1L;
    public static final int BOOK_1_QUANTITY = 3;

    public static final long BOOK_2_ID = 2L;
    public static final String BOOK_2_NAME = "Effective Java";
    public static final String BOOK_2_AUTHOR = "Joshua Bloch";
    public static final long BOOK_2_DETAILS_ID = 2L;
    public static final int BOOK_2_QUANTITY = 7;

    public static final long USER_ID = 1L;
    public static final String USER_1_FIRST_NAME = "firstName";
    public static final String USER_1_LAST_NAME = "lastName";

    public static List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        books.add(createBook(BOOK_1_ID, BOOK_1_NAME, BOOK_1_AUTHOR, BOOK_1_DETAILS_ID, BOOK_1_QUANTITY));
        books.add(createBook(BOOK_2_ID, BOOK_2_NAME, BOOK_2_AUTHOR, BOOK_2_DETAILS_ID, BOOK_2_QUANTITY));
        return books;
    }

    public static List<Book> getAvailableBook() {
        List<Book> books = new ArrayList<>();
        books.add(createBook(BOOK_1_ID, BOOK_1_NAME, BOOK_1_AUTHOR, BOOK_1_DETAILS_ID, BOOK_1_QUANTITY));
        return books;
    }

    public static List<Book> getBooksWithNullIds() {
        List<Book> books = new ArrayList<>();
        books.add(createBook(null, BOOK_1_NAME, BOOK_1_AUTHOR, null, BOOK_1_QUANTITY));
        books.add(createBook(null, BOOK_2_NAME, BOOK_2_AUTHOR, null, BOOK_2_QUANTITY));
        return books;
    }


    private static Book createBook(Long bookId, String bookName, String author, Long bookDetailsId, int quantity) {
        BookDetails bookDetails = new BookDetails();
        bookDetails.setId(bookDetailsId);
        bookDetails.setQuantity(quantity);
        Book book = new Book();
        book.setId(bookId);
        book.setName(bookName);
        book.setAuthor(author);
        book.setBookDetails(bookDetails);
        return book;
    }
}
