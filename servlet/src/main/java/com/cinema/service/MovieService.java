package com.cinema.service;

import java.util.ArrayList;
import java.util.List;

import com.cinema.dao.DaoFactory;
import com.cinema.dao.MovieDao;
import com.cinema.entity.Movie;
import com.cinema.exception.IdNotFoundException;
import com.cinema.exception.RuntimeSQLException;
import com.cinema.exception.TitleExistsException;
import com.cinema.exception.TitleNotFoundException;

public class MovieService {
	
	private MovieDao repo = DaoFactory.getInstance().createMovieDao();
	
	public Movie findByTitle(String title) throws TitleNotFoundException {
		try {
			return repo.findByTitle(title);
		} catch (RuntimeException e) {
			throw new TitleNotFoundException("There is no movie with this title: " + title);
		}
	}
	
	public List<Movie> findByTitleStartsWith(String title) {
		try {
			return repo.findByTitleStartsWith(title);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public Movie create(Movie newMovie) throws TitleExistsException {
		try {
			return repo.create(newMovie);
		} catch (RuntimeException e) {
			throw new TitleExistsException("There is already exists movie with this title: " + newMovie.getTitle());
		}
	}

	public List<Movie> findAll(int page, int size) {
		try {
			return repo.findAll(page, size); 
		} catch (RuntimeException e) {
			return new ArrayList<>();
		}
	}

	public long findCount() {
		try {
			return repo.findCount();
		} catch (RuntimeException e) {
			return 0;
		}
	}

	public Movie findById(long id) throws IdNotFoundException {
		try {
			return repo.findById(id);
		} catch (RuntimeSQLException e) {
			throw new IdNotFoundException("There is no movie with this id: " + id);
		}
	}

	public Movie update(Movie movie) throws IdNotFoundException {
		try {
			return repo.update(movie);
		} catch (RuntimeSQLException e) {
			throw new IdNotFoundException("Thre is no movie for updating with this id: " + movie.getId());
		}
	}

	public List<Movie> findByAll() {
		try {
			return repo.findAll(); 
		} catch (RuntimeException e) {
			return new ArrayList<>();
		}
	}
	
}
