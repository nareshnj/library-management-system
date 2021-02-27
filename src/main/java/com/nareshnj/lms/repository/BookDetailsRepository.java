package com.nareshnj.lms.repository;

import com.nareshnj.lms.entity.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookDetailsRepository extends JpaRepository<BookDetails, Long> {

    @Query("SELECT bd FROM BookDetails bd WHERE bd.book.id IN :books AND bd.quantity > 0")
    List<BookDetails> getAvailableBookListByIds(@Param("books") Set<Long> books);


}
