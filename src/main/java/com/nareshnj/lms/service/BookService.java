package com.nareshnj.lms.service;

import com.nareshnj.lms.entity.Book;
import com.nareshnj.lms.pojo.RegisterEntryRequest;

import java.util.List;
import java.util.Set;

public interface BookService {

    List<Book> getAll();

    Book getBookById(long id);

    List<Book> getAvailableBooksForUser(RegisterEntryRequest entryRequest);
}
