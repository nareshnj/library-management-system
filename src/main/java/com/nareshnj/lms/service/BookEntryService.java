package com.nareshnj.lms.service;

import com.nareshnj.lms.entity.Book;
import com.nareshnj.lms.entity.BookEntry;
import com.nareshnj.lms.pojo.RegisterEntryRequest;

import java.util.List;

public interface BookEntryService {

    List<BookEntry> getBorrowedBookEntriesByUserId(long userId);

    List<Book> getBorrowedBooksByUserId(long userId);

    void updateAll(List<BookEntry> bookEntries);

    List<Long> getBorrowedBookIdsByUserId(Long userId);
}
