package com.nareshnj.lms.service;

import com.nareshnj.lms.entity.Book;
import com.nareshnj.lms.entity.RegisterEntry;
import com.nareshnj.lms.pojo.RegisterEntryRequest;
import com.nareshnj.lms.pojo.Response;

import java.util.List;

public interface RegisterEntryService {

    List<RegisterEntry> getAllRegisterEntries();
    
    Response createEntry(RegisterEntryRequest entry);

    void updateBookCount(List<Book> books, String entryType);

}
