package com.nareshnj.lms.service.impl;

import com.nareshnj.lms.entity.*;
import com.nareshnj.lms.exception.SameBookBorrowException;
import com.nareshnj.lms.exception.LimitExceedException;
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
import java.util.stream.Collectors;

@Service
public class RegisterEntryServiceImpl implements RegisterEntryService {

    private static final int BOOKS_QUANTITY_LIMIT = 2;
    private static final String ENTRY_TYPE_BORROW = "BORROW";
    private static final String ENTRY_TYPE_RETURN = "RETURN";

    private final BookService bookService;
    private final RegisterEntryRepository registerEntryRepository;
    private final BookDetailsService bookDetailsService;
    private final BookEntryService bookEntryService;

    public RegisterEntryServiceImpl(BookService bookService, RegisterEntryRepository registerEntryRepository, BookDetailsService bookDetailsService, BookEntryService bookEntryService) {
        this.bookService = bookService;
        this.registerEntryRepository = registerEntryRepository;
        this.bookDetailsService = bookDetailsService;
        this.bookEntryService = bookEntryService;
    }

    @Override
    public List<RegisterEntry> getAllRegisterEntries() {
        return registerEntryRepository.findByOrderByCreatedDateTimeDesc();
    }

    @Override
    public Response createEntry(RegisterEntryRequest entry) {

        validateEntryRequest(entry);

        RegisterEntry registerEntry = mapToRegisterEntry(entry);
        registerEntryRepository.save(registerEntry);

        List<Book> books = bookService.getAvailableBooksForUser(entry);
        updateBookCount(books, registerEntry.getEntryType());
        inactivatePreviouslyBorrowedBookEntries(registerEntry);

        return new Response("SUCCESS", "Request processed successfully.");
    }

    private void validateEntryRequest(RegisterEntryRequest entryRequest) {
        if(ENTRY_TYPE_BORROW.equals(entryRequest.getEntryType())) {
            if (entryRequest.getBooks().size() > BOOKS_QUANTITY_LIMIT) {
                throw new LimitExceedException(String.format("User not allowed to borrow more than %d books.", BOOKS_QUANTITY_LIMIT));
            }

            List<Long> borrowedBookIds = bookEntryService.getBorrowedBookIdsByUserId(entryRequest.getUserId());
            if (BOOKS_QUANTITY_LIMIT < (borrowedBookIds.size() + entryRequest.getBooks().size())) {
                throw new LimitExceedException(String.format("User not allowed to borrow more than %d books.", BOOKS_QUANTITY_LIMIT));
            }

            List<Long> notAllowedBookIds = borrowedBookIds.stream().filter(bookId -> borrowedBookIds.contains(bookId)).collect(Collectors.toList());
            if (!notAllowedBookIds.isEmpty()) {
                throw new SameBookBorrowException(String.format("User already have copy of %s book.", notAllowedBookIds));
            }
        }

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

            if(ENTRY_TYPE_BORROW.equals(request.getEntryType())) {
                bookEntry.setActive(true);
            }else {
                bookEntry.setActive(false);
            }

            bookEntrySet.add(bookEntry);
        }
        entry.setBookEntries(bookEntrySet);
        entry.setEntryType(request.getEntryType());
        entry.setCreatedDateTime(LocalDateTime.now());

        return entry;
    }
}
