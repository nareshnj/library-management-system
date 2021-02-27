package com.nareshnj.lms.repository;

import com.nareshnj.lms.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.id IN :books AND b.bookDetails.quantity > 0")
    List<Book> getAvailableBookListByIds(@Param("books") Set<Long> books);

}
