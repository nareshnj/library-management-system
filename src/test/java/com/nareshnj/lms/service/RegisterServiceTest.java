package com.nareshnj.lms.service;

import com.nareshnj.lms.entity.RegisterEntry;
import com.nareshnj.lms.exception.SameBookBorrowException;
import com.nareshnj.lms.exception.LimitExceedException;
import com.nareshnj.lms.pojo.RegisterEntryRequest;
import com.nareshnj.lms.pojo.Response;
import com.nareshnj.lms.repository.RegisterEntryRepository;
import com.nareshnj.lms.service.impl.RegisterEntryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.nareshnj.lms.data.LibraryData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class RegisterServiceTest {

    public static final String ENTRY_TYPE_BORROW = "BORROW";

    @InjectMocks
    private RegisterEntryServiceImpl registerService;

    @Mock
    private BookService bookService;

    @Mock
    private RegisterEntryRepository registerEntryRepository;

    @Mock
    private BookDetailsService bookDetailsService;

    @Mock
    private BookEntryService bookEntryService;


    @Test
    public void test_whenBorrowedMoreThanLimit_then_rejectRequest() {
        RegisterEntryRequest entry = new RegisterEntryRequest();
        entry.setUserId(1L);
        entry.setEntryType(ENTRY_TYPE_BORROW);
        Set<Long> bookIds = new HashSet<>(Arrays.asList(1L, 2L, 3L, 4L));
        entry.setBooks(bookIds);

        LimitExceedException limitExceedException = assertThrows(LimitExceedException.class, () -> registerService.createEntry(entry));

        assertEquals("User not allowed to borrow more than 2 books.", limitExceedException.getMessage());
    }


    @Test
    public void test_whenUserBorrowedMultipleCopies_then_rejectRequest() {
        RegisterEntryRequest entry = new RegisterEntryRequest();
        entry.setUserId(1L);
        entry.setEntryType(ENTRY_TYPE_BORROW);
        Set<Long> bookIds = new HashSet<>(Arrays.asList(1L));
        entry.setBooks(bookIds);

        when(bookEntryService.getBorrowedBookIdsByUserId(entry.getUserId())).thenReturn(new ArrayList<>(bookIds));

        SameBookBorrowException sameBookBorrowException = assertThrows(SameBookBorrowException.class, () -> registerService.createEntry(entry));

        String expectedMessage = String.format("User already have copy of %s book.", bookIds);
        assertEquals(expectedMessage, sameBookBorrowException.getMessage());
    }

    @Test
    public void test_whenUserBorrowBooksInLimit_and_booksAvailableInLibrary_then_processRequest() {

        RegisterEntryRequest entryRequest = createRegisterEntryRequest();
        when(bookService.getAvailableBooksForUser(entryRequest)).thenReturn(getBooks());

        Response response = registerService.createEntry(entryRequest);

        verify(registerEntryRepository, times(1)).save(any(RegisterEntry.class));
        verify(bookDetailsService, times(1)).saveAll(anyList());
        assertEquals("SUCCESS", response.getStatus());
    }


    private RegisterEntryRequest createRegisterEntryRequest() {
        RegisterEntryRequest entry = new RegisterEntryRequest();
        entry.setUserId(1L);
        Set<Long> bookIds = new HashSet<>();
        bookIds.add(BOOK_1_ID);
        bookIds.add(BOOK_2_ID);
        entry.setBooks(bookIds);
        entry.setEntryType(ENTRY_TYPE_BORROW);
        return entry;
    }
}
