package com.cinema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cinema.dto.MovieDto;
import com.cinema.entity.Movie;
import com.cinema.exception.IdNotFoundException;
import com.cinema.exception.TitleExistsException;
import com.cinema.exception.TitleNotFoundException;
import com.cinema.repository.MovieRepository;

import lombok.NonNull;

@Service
public class MovieService {
	
	@Autowired
	MovieRepository repository;
	
	public List<Movie> findAll() {
		return repository.findAll();
	}
	
	public Page<Movie> findAll(int page, int size) {
		return repository.findAll(PageRequest.of(page, size));
	}
	
	public Movie findById(long id) throws IdNotFoundException {
		return repository.findById(id).orElseThrow(() -> new IdNotFoundException("There isn't an movie with that id: " + id));
	}
	
	public Movie findByTitle(@NonNull String title) throws TitleNotFoundException {
		return repository.findByTitle(title).orElseThrow(() -> new TitleNotFoundException("There isn't an movie with that title: " + title));
	}
	
	public Movie addNewMovie(@NonNull MovieDto movieDto) throws TitleExistsException {
		try {
			return repository.save(toMovie(movieDto));
		} catch (Exception e) {
			throw new TitleExistsException("There is a movie with that title: " + movieDto.getTitle());
		}
	}
	
	public Movie update(@NonNull Movie movie) {
		return repository.save(movie);
	}
	
	private Movie toMovie(MovieDto movieDto) {
		return Movie.builder()
				.title(movieDto.getTitle())
				.duration(movieDto.getDuration())
				.description(movieDto.getDescription())
				.build();
	}
	
}
