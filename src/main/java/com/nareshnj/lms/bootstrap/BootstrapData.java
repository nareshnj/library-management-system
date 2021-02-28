package com.nareshnj.lms.bootstrap;

import com.nareshnj.lms.entity.Book;
import com.nareshnj.lms.entity.BookDetails;
import com.nareshnj.lms.entity.User;
import com.nareshnj.lms.repository.BookRepository;
import com.nareshnj.lms.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BootstrapData implements CommandLineRunner {

    private UserRepository userRepository;
    private BookRepository bookRepository;

    public BootstrapData(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        addUser();
        addBook();
    }

    private void addUser() {
        List<User> userList = new ArrayList<>();
        userList.add(createUser("George", "Burdell"));
        userList.add(createUser("Tom", "Collins"));
        userList.add(createUser("Anthony", "Johnson"));
        userRepository.saveAll(userList);
    }

    private User createUser(String firstName, String lastName) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }

    private void addBook() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(createBook("Effective Java", "Joshua Bloch", 4));
        bookList.add(createBook("Head First Java", "Kathy Sierra", 5));
        bookList.add(createBook("Test-Driven: TDD", "Lasse Koskela", 2));
        bookRepository.saveAll(bookList);
    }

    private Book createBook(String name, String author, int quantity) {
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        BookDetails bookDetails = new BookDetails();
        bookDetails.setQuantity(quantity);
        book.setBookDetails(bookDetails);
        return book;
    }

}
