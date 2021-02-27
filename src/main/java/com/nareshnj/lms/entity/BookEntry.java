package com.nareshnj.lms.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class BookEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @ManyToOne
    private RegisterEntry registerEntry;

    private int quantity;

}
