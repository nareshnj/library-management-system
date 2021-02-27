package com.nareshnj.lms.service;

import com.nareshnj.lms.entity.Book;
import com.nareshnj.lms.entity.RegisterEntry;
import com.nareshnj.lms.pojo.RegisterEntryRequest;
import com.nareshnj.lms.pojo.Response;
import com.nareshnj.lms.repository.BookDetailsRepository;
import com.nareshnj.lms.repository.RegisterEntryRepository;
import com.nareshnj.lms.service.impl.RegisterEntryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.nareshnj.lms.data.LibraryData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class RegisterServiceTest {

    public static final String ENTRY_TYPE_BORROW = "BORROW";
    public static final String ENTRY_TYPE_RETURN = "RETURN";

    @InjectMocks
    private RegisterEntryServiceImpl registerService;

    @Mock
    private BookService bookService;

    @Mock
    private RegisterEntryRepository registerEntryRepository;

    @Mock
    private BookDetailsRepository bookDetailsRepository;


    @Test
    public void test_whenBorrowedMoreThanLimit_then_rejectRequest() {
        RegisterEntryRequest entry = new RegisterEntryRequest();
        entry.setUserId(1L);
        Set<Long> bookIds = new HashSet<>(Arrays.asList(1L, 2L, 3L, 4L));
        entry.setBooks(bookIds);

        Response response = registerService.createEntry(entry);

        assertEquals("ERROR", response.getStatus());
    }

    @Test
    public void test_whenUserBorrowBooksInLimit_butNotAvailableInLibrary_then_rejectRequest() {

        RegisterEntryRequest entryRequest = createRegisterEntryRequest();
        when(bookService.getAvailableBookListByIds(entryRequest.getBooks())).thenReturn(getAvailableBook());

        Response response = registerService.createEntry(entryRequest);

        assertEquals("ERROR", response.getStatus());
    }

    @Test
    public void test_whenUserBorrowBooksInLimit_and_booksAvailableInLibrary_then_processRequest() {

        RegisterEntryRequest entryRequest = createRegisterEntryRequest();
        when(bookService.getAvailableBookListByIds(entryRequest.getBooks())).thenReturn(getBooks());

        Response response = registerService.createEntry(entryRequest);

        verify(registerEntryRepository, times(1)).save(any(RegisterEntry.class));
        verify(bookDetailsRepository, times(1)).saveAll(anyList());
        assertEquals("SUCCESS", response.getStatus());
    }


    private RegisterEntryRequest createRegisterEntryRequest() {
        RegisterEntryRequest entry = new RegisterEntryRequest();
        entry.setUserId(1L);
        Set<Long> bookIds = new HashSet<>();
        bookIds.add(BOOK_1_ID);
        bookIds.add(BOOK_2_ID);
        entry.setBooks(bookIds);
        entry.setRequestType("BORROW");
        return entry;
    }
}
