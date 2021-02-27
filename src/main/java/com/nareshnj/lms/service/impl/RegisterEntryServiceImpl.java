package com.nareshnj.lms.service.impl;

import com.nareshnj.lms.entity.*;
import com.nareshnj.lms.pojo.RegisterEntryRequest;
import com.nareshnj.lms.pojo.Response;
import com.nareshnj.lms.repository.RegisterEntryRepository;
import com.nareshnj.lms.service.BookDetailsService;
import com.nareshnj.lms.service.BookEntryService;
import com.nareshnj.lms.service.BookService;
import com.nareshnj.lms.service.RegisterEntryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RegisterEntryServiceImpl implements RegisterEntryService {

    private static final int BOOKS_QUANTITY_LIMIT = 2;
    private static final String ENTRY_TYPE_BORROW = "BORROW";
    private static final String ENTRY_TYPE_RETURN = "RETURN";

    private BookService bookService;
    private RegisterEntryRepository registerEntryRepository;
    private BookDetailsService bookDetailsService;
    private BookEntryService bookEntryService;

    public RegisterEntryServiceImpl(BookService bookService, RegisterEntryRepository registerEntryRepository, BookDetailsService bookDetailsService, BookEntryService bookEntryService) {
        this.bookService = bookService;
        this.registerEntryRepository = registerEntryRepository;
        this.bookDetailsService = bookDetailsService;
        this.bookEntryService = bookEntryService;
    }

    @Override
    public Response createEntry(RegisterEntryRequest entry) {
        Response response;
        if (entry.getBooks().size() > BOOKS_QUANTITY_LIMIT) {
            response = new Response("ERROR", String.format("User not allowed to borrow more than %d books.", BOOKS_QUANTITY_LIMIT));
            return response;
        }

        List<Book> books = bookService.getAvailableBooksForUser(entry);

        if (ENTRY_TYPE_BORROW.equals(entry.getRequestType()) && books.size() < entry.getBooks().size()) {
            response = new Response("ERROR", "One or more requested books not available.");
            return response;
        }

        RegisterEntry registerEntry = mapToRegisterEntry(entry);
        registerEntryRepository.save(registerEntry);
        updateBookCount(books, registerEntry.getEntryType());
        inactivatePreviouslyBorrowedBookEntries(registerEntry);

        return new Response("SUCCESS", "Request processed successfully.");
    }

    private void inactivatePreviouslyBorrowedBookEntries(RegisterEntry registerEntry) {
        if(ENTRY_TYPE_RETURN.equals(registerEntry.getEntryType())) {
            List<BookEntry> borrowedBookEntries = bookEntryService.getBorrowedBookEntriesByUserId(registerEntry.getUser().getId());
            List<BookEntry> inactivateEntries = new ArrayList<>();
            for(BookEntry bookEntry: registerEntry.getBookEntries()) {
                for(BookEntry borrowedBookEntry : borrowedBookEntries) {
                    if(bookEntry.getBook().getId() == borrowedBookEntry.getBook().getId()) {
                        borrowedBookEntry.setActive(false);
                        inactivateEntries.add(borrowedBookEntry);
                    }
                }
            }

            if(!inactivateEntries.isEmpty()) {
                bookEntryService.updateAll(inactivateEntries);
            }
        }
    }

    @Override
    public void updateBookCount(List<Book> books, String entryType) {
        List<BookDetails> updatedBookDetails = new ArrayList<>();
        for (Book book : books) {
            BookDetails bookDetails = book.getBookDetails();
            int quantity;
            if (ENTRY_TYPE_BORROW.equals(entryType)) {
                quantity = bookDetails.getQuantity() - 1;
            } else {
                quantity = bookDetails.getQuantity() + 1;
            }
            bookDetails.setQuantity(quantity);
            updatedBookDetails.add(bookDetails);
        }

        bookDetailsService.saveAll(updatedBookDetails);
    }

    private RegisterEntry mapToRegisterEntry(RegisterEntryRequest request) {
        RegisterEntry entry = new RegisterEntry();
        User user = new User();
        user.setId(request.getUserId());
        entry.setUser(user);

        Set<BookEntry> bookEntrySet = new HashSet<>();
        for (long id : request.getBooks()) {
            BookEntry bookEntry = new BookEntry();

            bookEntry.setQuantity(1);

            Book book = new Book();
            book.setId(id);
            bookEntry.setBook(book);

            if(ENTRY_TYPE_BORROW.equals(request.getRequestType())) {
                bookEntry.setActive(true);
            }else {
                bookEntry.setActive(false);
            }

            bookEntrySet.add(bookEntry);
        }
        entry.setBookEntries(bookEntrySet);
        entry.setEntryType(request.getRequestType());
        entry.setCreatedDateTime(LocalDateTime.now());

        return entry;
    }
}
