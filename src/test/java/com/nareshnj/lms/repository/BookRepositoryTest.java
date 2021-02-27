package com.nareshnj.lms.repository;

import com.nareshnj.lms.data.LibraryData;
import com.nareshnj.lms.entity.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void test_saveBook() {
        Book book = LibraryData.getBooksWithNullIds().get(0);

        bookRepository.save(book);

        assertNotNull(book.getId());
        assertNotNull(book.getBookDetails().getId());
        assertEquals(book, bookRepository.getOne(book.getId()));
    }

}
