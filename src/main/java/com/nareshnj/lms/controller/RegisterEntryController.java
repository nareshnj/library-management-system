package com.nareshnj.lms.controller;

import com.nareshnj.lms.entity.Book;
import com.nareshnj.lms.entity.RegisterEntry;
import com.nareshnj.lms.pojo.RegisterEntryRequest;
import com.nareshnj.lms.pojo.Response;
import com.nareshnj.lms.service.BookEntryService;
import com.nareshnj.lms.service.RegisterEntryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/v1/register")
public class RegisterEntryController {

    private final RegisterEntryService registerEntryService;
    private final BookEntryService bookEntryService;

    public RegisterEntryController(RegisterEntryService registerEntryService, BookEntryService bookEntryService) {
        this.registerEntryService = registerEntryService;
        this.bookEntryService = bookEntryService;
    }

    @GetMapping
    public List<RegisterEntry> getRegisterEntry() {
        return registerEntryService.getAllRegisterEntries();
    }

    @PostMapping
    public Response createRegisterEntry(@RequestBody RegisterEntryRequest request) {
        return registerEntryService.createEntry(request);
    }

    @GetMapping("/entry/{userId}")
    public List<Book> getBorrowedBooksForUsers(@PathVariable("userId") long userId) {
        return bookEntryService.getBorrowedBooksByUserId(userId);
    }

}
