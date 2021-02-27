package com.nareshnj.lms.repository;

import com.nareshnj.lms.entity.BookEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookEntryRepository extends JpaRepository<BookEntry, Long> {

    @Query("SELECT be FROM BookEntry be WHERE be.registerEntry.user.id = ?1 AND be.registerEntry.entryType = 'BORROW' AND be.isActive = true")
    List<BookEntry> getBorrowedBookEntriesByUserId(long userId);
}
