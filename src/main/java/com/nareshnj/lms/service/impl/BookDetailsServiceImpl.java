package com.nareshnj.lms.service.impl;

import com.nareshnj.lms.entity.BookDetails;
import com.nareshnj.lms.repository.BookDetailsRepository;
import com.nareshnj.lms.service.BookDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BookDetailsServiceImpl implements BookDetailsService {

    private final BookDetailsRepository bookDetailsRepository;

    public BookDetailsServiceImpl(BookDetailsRepository bookDetailsRepository) {
        this.bookDetailsRepository = bookDetailsRepository;
    }

    @Override
    public List<BookDetails> findALl() {
        return bookDetailsRepository.findAll();
    }

    @Override
    public List<BookDetails> getAvailableBookListByIds(Set<Long> books) {
        return bookDetailsRepository.getAvailableBookListByIds(books);
    }

    @Override
    public void saveAll(List<BookDetails> updatedBookDetails) {
        bookDetailsRepository.saveAll(updatedBookDetails);
    }
}
