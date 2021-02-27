package com.nareshnj.lms.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class BookDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "bookDetails")
    private Book book;

    private int quantity;

}
