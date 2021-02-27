package com.nareshnj.lms.repository;

import com.nareshnj.lms.entity.RegisterEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterEntryRepository extends JpaRepository<RegisterEntry, Long> {
}
