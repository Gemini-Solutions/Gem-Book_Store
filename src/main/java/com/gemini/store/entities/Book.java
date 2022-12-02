package com.gemini.store.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookISIN;
    private String booktitle;
    private String publishdate;
    private String edition;
    @ManyToOne(cascade = CascadeType.ALL)
    private Author authorId;
    @ManyToOne(cascade = CascadeType.ALL)
    private Publisher publisherId;

}
