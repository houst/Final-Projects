package com.cinema.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.cinema.dto.MovieDto;
import com.cinema.entity.Movie;
import com.cinema.exception.TitleExistsException;
import com.cinema.exception.TitleNotFoundException;
import com.cinema.service.MovieService;

@RestController
@RequestMapping("/movie")
public class MovieController {
	
	private static final int DEFAULT_PAGE_NUMBER = 1;
	
	private static final int DEFAULT_PAGE_SIZE = 10;
	
	@Autowired
	MovieService service;
	
	@PostMapping
	public ResponseEntity<String> addNewMovie(
			@Valid MovieDto movieDto, 
			BindingResult result) 
					throws TitleExistsException
	{	
		
		Optional.ofNullable(result.getFieldError()).ifPresent(e -> {throw new ResponseStatusException(HttpStatus.BAD_REQUEST);});
	    
	    service.addNewMovie(movieDto);
	    
	    return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping
	public List<Movie> getAllMovies() {
		return service.findAll();
	}
	
	@GetMapping(params = {"page", "size"})
	public Page<Movie> getPaginated(
			@RequestParam Optional<Integer> page,
			@RequestParam Optional<Integer> size) 
	{
		return service.findAll(page.orElse(DEFAULT_PAGE_NUMBER) - 1, size.orElse(DEFAULT_PAGE_SIZE));
	}
	
	@GetMapping(params = {"title"})
	public Movie getMovieByTitle(@RequestParam String title) throws TitleNotFoundException {	
		return service.findByTitle(title);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("{movie}")
	public ResponseEntity<String> movieUpdate(
			@RequestParam String title,
			@RequestParam int duration,
			@RequestParam String description,
			@RequestParam("movieId") Movie movie) 
	{
		
		movie.setTitle(title);
		movie.setDuration(duration);
		movie.setDescription(description);
		
		service.update(movie);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
