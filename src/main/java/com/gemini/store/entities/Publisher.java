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
public class Publisher {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer publisherId;
	private String publisherName;
	private String publisherNationality;
	@OneToMany(mappedBy = "publisherId",cascade = CascadeType.ALL)
    private List<Book> books;
}
