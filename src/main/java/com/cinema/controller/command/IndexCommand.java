package com.cinema.controller.command;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cinema.entity.Movie;
import com.cinema.entity.Seance;
import com.cinema.service.MovieService;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

public class IndexCommand implements Command {

	MovieService movieService = new MovieService();
	
	@Override
	public String execute(HttpServletRequest request) {
		List<Movie> movies = movieService.findByAll();
		Map<Movie, Multimap<LocalDate, Seance>> regroupedMovies = new HashMap<>();
		movies.stream()
				.forEach(movie -> {
					Multimap<LocalDate, Seance> regroupedSeances = LinkedListMultimap.create();
					movie.getSeances().stream()
							.forEach(seance -> 
								regroupedSeances.put(seance.getStartDateTime().toLocalDate(), seance)
							);
					regroupedMovies.put(movie, regroupedSeances);
				});
		request.setAttribute("movies", regroupedMovies);
		return "/WEB-INF/index.jsp";
	}

}
