package com.nareshnj.lms.repository;

import com.nareshnj.lms.data.LibraryData;
import com.nareshnj.lms.entity.Book;
import com.nareshnj.lms.entity.BookEntry;
import com.nareshnj.lms.entity.RegisterEntry;
import com.nareshnj.lms.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RegisterEntryRepositoryTest {

    public static final String ENTRY_TYPE_BORROW = "BORROW";

    @Autowired
    private RegisterEntryRepository registerEntryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookEntryRepository bookEntryRepository;

    private User testUser;
    private List<Book> books;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setFirstName("testFirstName");
        testUser.setLastName("testLastName");
        userRepository.save(testUser);

        books = LibraryData.getBooksWithNullIds();
        bookRepository.saveAll(books);

    }

    @Test
    public void test_saveRegisterEntry_with_bookEntry() {

        RegisterEntry registerEntry = getRegisterEntry();
        registerEntryRepository.save(registerEntry);

        assertNotNull(registerEntry.getId());
        assertEquals(registerEntry.getBookEntries().size(), bookEntryRepository.findAll().size());
    }

    private RegisterEntry getRegisterEntry() {
        RegisterEntry registerEntry = new RegisterEntry();

        User user = new User();
        user.setId(testUser.getId());
        registerEntry.setUser(user);

        Set<Long> bookIds = books.stream().map(book -> book.getId()).collect(Collectors.toSet());
        Set<BookEntry> bookEntrySet = new HashSet<>();
        for (long id : bookIds) {
            BookEntry bookEntry = new BookEntry();
            bookEntry.setQuantity(1);
            Book book = new Book();
            book.setId(id);
            bookEntry.setBook(book);
            bookEntrySet.add(bookEntry);
        }
        registerEntry.setEntryType(ENTRY_TYPE_BORROW);
        registerEntry.setBookEntries(bookEntrySet);

        return registerEntry;
    }
}
