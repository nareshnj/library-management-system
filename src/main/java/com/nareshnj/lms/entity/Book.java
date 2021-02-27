package com.nareshnj.lms.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
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

}
