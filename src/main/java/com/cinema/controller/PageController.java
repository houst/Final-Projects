package com.cinema.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinema.entity.Movie;
import com.cinema.entity.Seance;
import com.cinema.entity.User;
import com.cinema.exception.IdNotFoundException;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

@Controller
@RequestMapping("/{locale:en|ua}")
public class PageController {
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private RoleController roleController;
	
	@Autowired
	private MovieController movieController;
	
	@Autowired
	private SeanceController seanceController;

	@Autowired
	private TicketController ticketController;
	
	@GetMapping
	public String index(Model model) {
		
		List<Movie> movies = movieController.getAllMovies();
		// TODO: Убрать двойной цикл
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
		model.addAttribute("movies", regroupedMovies);
		
		return "index";
	}
	
	@GetMapping({"/seance/{seance}"})
	public String seance(
			@PathVariable Optional<Seance> seance,
			Model model) 
					throws IdNotFoundException 
	{
		model.addAttribute("seanceAtt", seance.orElseThrow(() -> new IdNotFoundException("There is no seance with returned id from context path")));
		return "seance";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping({"/profile"})
	public String profile(Model model) throws IdNotFoundException {
		User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user", currentUser);
		model.addAttribute("tickets", ticketController.getTicketsByUser(Optional.of(currentUser)));
		
		return "profile";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping({"/users"})
	public String userList(Model model) {
		model.addAttribute("users", userController.getAllUsers());
		return "user-list";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/user/{user}/edit")
	public String userEdit(
			@PathVariable Optional<User> user,
			Model model) 
					throws IdNotFoundException 
	{
		model.addAttribute("user", user.orElseThrow(() -> 
									new IdNotFoundException("There isn't an user with that id returned from context path")));
		model.addAttribute("roles", roleController.getAllRoles());
		return "user-edit";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping({"/movies"})
	public String movieList(
			@RequestParam Optional<Boolean> movieadded,
			@RequestParam Optional<Integer> page,
			@RequestParam Optional<Integer> size,
			Model model) 
	{
		Page<Movie> paginatedMovies = movieController.getPaginated(page, size);
		model.addAttribute("movies", paginatedMovies);
		model.addAttribute("pageNumbers", IntStream.rangeClosed(1, paginatedMovies.getTotalPages())
				.boxed()
                .collect(Collectors.toList()));
		model.addAttribute("movieadded", movieadded.orElse(false));
		return "movie-list";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/movie/{movie}/edit")
	public String movieEdit(
			@PathVariable Optional<Movie> movie,
			Model model) 
					throws IdNotFoundException 
	{
		model.addAttribute("movie", movie.orElseThrow(() -> 
									new IdNotFoundException("There isn't an movie with that id returned from context path")));
		return "movie-edit";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/movie/add")
	public String addMovie() {
		return "movie-add";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping({"/seances"})
	public String seanceList(
			@RequestParam Optional<Boolean> seanceadded,
			@RequestParam Optional<Integer> page,
			@RequestParam Optional<Integer> size,
			Model model) 
	{
		Page<Seance> paginatedSeances = seanceController.getPaginated(page, size);
		model.addAttribute("seances", paginatedSeances);
		model.addAttribute("pageNumbers", IntStream.rangeClosed(1, paginatedSeances.getTotalPages())
				.boxed()
                .collect(Collectors.toList()));
		model.addAttribute("seanceadded", seanceadded.orElse(false));
		return "seance-list";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/seance/add")
	public String addSeance(
			@RequestParam Optional<Boolean> added,
			Model model) {
		model.addAttribute("movies", movieController.getAllMovies());
		model.addAttribute("added", added.orElse(false));
		return "seance-add";
	}
	
}
