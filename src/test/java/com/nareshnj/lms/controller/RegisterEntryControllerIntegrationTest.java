package com.nareshnj.lms.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nareshnj.lms.LibraryManagementApplication;
import com.nareshnj.lms.data.LibraryData;
import com.nareshnj.lms.entity.Book;
import com.nareshnj.lms.entity.BookDetails;
import com.nareshnj.lms.entity.RegisterEntry;
import com.nareshnj.lms.entity.User;
import com.nareshnj.lms.pojo.RegisterEntryRequest;
import com.nareshnj.lms.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LibraryManagementApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegisterEntryControllerIntegrationTest {

    public static final String ENTRY_TYPE_BORROW = "BORROW";
    public static final String ENTRY_TYPE_RETURN = "RETURN";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RegisterEntryController registerEntryController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookEntryRepository bookEntryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RegisterEntryRepository registerEntryRepository;
    @Autowired
    private BookDetailsRepository bookDetailsRepository;

    private User user;
    private List<Book> initialBookList;

    @BeforeAll
    public void commonSetUp() {
        user = new User();
        user.setFirstName("Naresh");
        user.setLastName("Jagadale");
        userRepository.save(user);
    }

    @BeforeEach
    public void setUp() {
        initialBookList = LibraryData.getBooksWithNullIds();
        bookRepository.saveAll(initialBookList);
    }

    @AfterEach
    public void cleanUp() {
        bookEntryRepository.deleteAllInBatch();
        registerEntryRepository.deleteAllInBatch();
        bookRepository.deleteAllInBatch();
        bookDetailsRepository.deleteAllInBatch();
    }

    @Test
    public void test_givenBooksPresentInLibrary_whenUserBorrowBook_thenBookAddedBorrowedList_andRemovedFromLibrary() throws Exception {

        RegisterEntryRequest entryRequest = createRegisterEntryRequest(ENTRY_TYPE_BORROW);

        mockMvc.perform(post("/v1/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(entryRequest)))
                .andExpect(status().isOk());

        Set<Long> books = entryRequest.getBooks();
        List<RegisterEntry> registerEntries = registerEntryRepository.findByUserId(user.getId());

        assertEquals(1, registerEntries.size());
        assertEquals(ENTRY_TYPE_BORROW, registerEntries.get(0).getEntryType());
        assertEquals(books.size(), registerEntries.get(0).getBookEntries().size());

        assertLibraryBookCount(ENTRY_TYPE_BORROW);
    }

    @Test
    public void test_givenBooksPresentInLibrary_whenUserReturnBook_thenBookRemovedFromBorrowedList_andAddedInLibrary() throws Exception {

        RegisterEntryRequest entryRequest = createRegisterEntryRequest(ENTRY_TYPE_RETURN);

        mockMvc.perform(post("/v1/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(entryRequest)))
                .andExpect(status().isOk());

        Set<Long> books = entryRequest.getBooks();
        List<RegisterEntry> registerEntries = registerEntryRepository.findByUserId(user.getId());

        assertEquals(1, registerEntries.size());
        assertEquals(ENTRY_TYPE_RETURN, registerEntries.get(0).getEntryType());
        assertEquals(books.size(), registerEntries.get(0).getBookEntries().size());

        assertLibraryBookCount(ENTRY_TYPE_RETURN);
    }

    private void assertLibraryBookCount(String entryType) {
        List<BookDetails> bookDetailsList = bookDetailsRepository.findAll();
        for (Book book : initialBookList) {
            for (BookDetails bookDetails : bookDetailsList) {
                if (book.getBookDetails().getId() == bookDetails.getId()) {
                    int expectedQuantity;
                    if (ENTRY_TYPE_BORROW.equals(entryType)) {
                        expectedQuantity = book.getBookDetails().getQuantity() - 1;
                    } else {
                        expectedQuantity = book.getBookDetails().getQuantity() + 1;
                    }
                    assertEquals(expectedQuantity, bookDetails.getQuantity());
                }
            }
        }
    }

    private RegisterEntryRequest createRegisterEntryRequest(String entryType) {
        RegisterEntryRequest entry = new RegisterEntryRequest();
        entry.setUserId(user.getId());
        Set<Long> bookIds = bookRepository.findAll().stream().map(Book::getId).collect(Collectors.toSet());
        entry.setBooks(bookIds);
        entry.setRequestType(entryType);
        return entry;
    }
}
