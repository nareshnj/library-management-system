package com.nareshnj.lms.service.impl;

import com.nareshnj.lms.entity.Book;
import com.nareshnj.lms.pojo.RegisterEntryRequest;
import com.nareshnj.lms.repository.BookRepository;
import com.nareshnj.lms.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Book getBookById(long id) {
        return bookRepository.getOne(id);
    }

    @Override
    public List<Book> getBookListByIds(Set<Long> bookIds) {
        return bookRepository.getBookListByIds(bookIds);
    }

    @Override
    public List<Book> getAvailableBooksInLibrary(RegisterEntryRequest entryRequest) {
        return bookRepository.getAvailableBookListByIds(entryRequest.getBooks());
    }

}
