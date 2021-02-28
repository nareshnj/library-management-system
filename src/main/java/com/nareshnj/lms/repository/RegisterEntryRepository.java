package com.nareshnj.lms.repository;

import com.nareshnj.lms.entity.RegisterEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegisterEntryRepository extends JpaRepository<RegisterEntry, Long> {

    //@Query("SELECT re from RegisterEntry re JOIN FETCH re.bookEntries")
    List<RegisterEntry> findByUserId(Long userId);

    List<RegisterEntry> findByOrderByCreatedDateTimeDesc();
}
