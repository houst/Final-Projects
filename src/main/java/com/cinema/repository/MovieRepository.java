package com.cinema.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
	
	Optional<Movie> findByTitle(String title);
	
	List<Movie> findAll();
	
}
