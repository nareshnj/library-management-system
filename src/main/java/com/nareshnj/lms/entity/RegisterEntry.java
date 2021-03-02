package com.nareshnj.lms.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class RegisterEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "register_entry_id")
    private Set<BookEntry> bookEntries;

    private String entryType;

    private LocalDateTime createdDateTime;

    @Override
    public String toString() {
        return "RegisterEntry{" +
                "id=" + id +
                ", user=" + user +
                ", bookEntries=" + bookEntries +
                ", entryType='" + entryType + '\'' +
                ", createdDateTime=" + createdDateTime +
                '}';
    }
}
