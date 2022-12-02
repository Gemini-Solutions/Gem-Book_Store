package com.gemini.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gemini.store.entities.Publisher;

import java.util.Optional;

public interface PublisherRepo extends JpaRepository<Publisher, Integer> {
	Optional<Publisher> findByPublisherName(String publisherName);
}
