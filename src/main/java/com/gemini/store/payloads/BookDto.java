package com.gemini.store.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
	private String booktitle;
	private String publishdate;
	private String edition;
	private String publisherName;
	private String publisherNationality;
	private String authorName;
	private String authorNationality;

}
