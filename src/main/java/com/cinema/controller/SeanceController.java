package com.cinema.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.cinema.dto.SeanceDto;
import com.cinema.entity.Seance;
import com.cinema.entity.Ticket;
import com.cinema.exception.IdNotFoundException;
import com.cinema.exception.SeanceBadTimeException;
import com.cinema.exception.SeanceTimeIsAlreadyTakenException;
import com.cinema.exception.TitleNotFoundException;
import com.cinema.service.SeanceService;

@RestController
@RequestMapping("/seance")
public class SeanceController {
	
	private static final int DEFAULT_PAGE_NUMBER = 1;
	
	private static final int DEFAULT_PAGE_SIZE = 10;
	
	@Autowired
	TicketController ticketController;
	
	@Autowired
	MovieController movieController;
	
	@Autowired
	SeanceService service;
	
	@GetMapping
	public Page<Seance> getPaginated(
			@RequestParam Optional<Integer> page,
			@RequestParam Optional<Integer> size) {
		return service.findAll(page.orElse(DEFAULT_PAGE_NUMBER) - 1, size.orElse(DEFAULT_PAGE_SIZE));
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public Seance addNewSeance(
			@Valid SeanceDto seanceDto, 
			BindingResult result) throws TitleNotFoundException, SeanceBadTimeException, SeanceTimeIsAlreadyTakenException {	
				
	    Optional.ofNullable(result.getFieldError()).ifPresent(e -> {throw new ResponseStatusException(HttpStatus.BAD_REQUEST);});
	    
	    return service.addNewSeance(
	    		seanceDto,
	    		ticketController.generateEmptyTickets(),
	    		movieController.getMovieByTitle(seanceDto.getMovieTitle()));
	}
	
	@PreAuthorize("isAuthenticated()")
	@PutMapping("{seance}/tickets")
	public ResponseEntity<String> addChosenTicketsOfCurrentSeanceToCurrentAuthUser(
			@RequestParam Map<String, String> checkboxes,
			@RequestParam("seanceId") Seance seance) {	
		List<Ticket> checkedTickets = checkboxes.keySet().stream()
				.filter(ticket -> ticket.startsWith("ticket"))
				.map(ticketId -> ticketId.substring(6))
				.map(Integer::new)
				.map(ticketId -> {
					try {
						return ticketController.getTicketById(ticketId);
					} catch (IdNotFoundException e) {
						return null;
					}
				})
				.collect(Collectors.toList());
		
		ticketController.addTicketsToCurrentUser(checkedTickets);
		
		return ResponseEntity.ok().build();
	}
	
}
