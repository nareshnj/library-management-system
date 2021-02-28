package com.nareshnj.lms.service.impl;

import com.nareshnj.lms.entity.BookEntry;
import com.nareshnj.lms.pojo.RegisterEntryRequest;
import com.nareshnj.lms.repository.BookEntryRepository;
import com.nareshnj.lms.service.BookEntryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookEntryServiceImpl implements BookEntryService {

    private final BookEntryRepository bookEntryRepository;

    public BookEntryServiceImpl(BookEntryRepository bookEntryRepository) {
        this.bookEntryRepository = bookEntryRepository;
    }

    @Override
    public List<BookEntry> getBorrowedBookEntriesByUserId(long userId) {
        return bookEntryRepository.getBorrowedBookEntriesByUserId(userId);
    }

    @Override
    public void updateAll(List<BookEntry> bookEntries) {
        bookEntryRepository.saveAll(bookEntries);
    }

    @Override
    public List<Long> getBorrowedBookIdsByUserId(Long userId) {
        return bookEntryRepository.getBorrowedBookIdsByUserId(userId);
    }
}
