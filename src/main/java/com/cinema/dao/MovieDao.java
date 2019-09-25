package com.cinema.dao;

import java.util.List;

import com.cinema.entity.Movie;
import com.cinema.entity.Seance;
import com.cinema.entity.User;

public interface MovieDao extends AutoCloseable {
	
	Movie findBySeance(Seance seance);
	
	List<Movie> findByUser(User user);

	Movie findByTitle(String title);

	Movie create(Movie newMovie);

	List<Movie> findAll(int page, int size);

	long findCount();

	Movie findById(long id);

	Movie update(Movie movie);

	List<Movie> findByTitleStartsWith(String titleStartsWith);

	List<Movie> findAll();
	
}
