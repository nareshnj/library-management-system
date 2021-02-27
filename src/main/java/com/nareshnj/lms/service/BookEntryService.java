package com.nareshnj.lms.service;

import com.nareshnj.lms.entity.BookEntry;

import java.util.List;

public interface BookEntryService {

    List<BookEntry> getBorrowedBookEntriesByUserId(long userId);

    void updateAll(List<BookEntry> bookEntries);
}
