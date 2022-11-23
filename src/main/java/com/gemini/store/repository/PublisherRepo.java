package com.gemini.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gemini.store.entities.Publisher;

public interface PublisherRepo extends JpaRepository<Publisher, Integer> {
	Publisher findByPublisherName(String publisherName);
}
