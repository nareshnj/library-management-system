package com.nareshnj.lms.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String author;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_details_id", referencedColumnName = "id")
    private BookDetails bookDetails;

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!name.equals(book.name)) return false;
        if (!author.equals(book.author)) return false;
        return bookDetails.equals(book.bookDetails);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + bookDetails.hashCode();
        return result;
    }*/
}
