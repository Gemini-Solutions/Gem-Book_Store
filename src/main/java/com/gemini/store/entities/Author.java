package com.gemini.store.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authorId;
    private String authorName;
    private String authorNationality;
    @OneToMany(mappedBy = "authorId")
    private List<Book> books;

    public Author(String authorName, String authorNationality) {
        this.authorName=authorName;
        this.authorNationality=authorNationality;
    }
}
