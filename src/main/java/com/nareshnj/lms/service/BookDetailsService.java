package com.nareshnj.lms.service;

import com.nareshnj.lms.entity.BookDetails;

import java.util.List;
import java.util.Set;

public interface BookDetailsService {

    List<BookDetails> findALl();

    List<BookDetails> getAvailableBookListByIds(Set<Long> books);

    void saveAll(List<BookDetails> updatedBookDetails);
}
