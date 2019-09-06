package com.cinema.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.cinema.entity.Ticket;
import com.cinema.entity.User;
import com.cinema.exception.IdNotFoundException;
import com.cinema.service.TicketService;

@RestController
@RequestMapping("/ticket")
public class TicketController {
	
	private static final int MAX_GENERATED_ROW = 3;
	
	private static final int MAX_GENERATED_CELL = 6;

	private static final String TICKET_PRICE = "10";
	
	@Autowired
	TicketService service;
	
	@GetMapping({"{ticketId}"})
	public Ticket getTicketById(
			@PathVariable Integer ticketId) throws IdNotFoundException 
	{
		return service.findById(ticketId);
	}
	
	@GetMapping({"user/{user}"})
	public List<Ticket> getTicketsByUser(
			@PathVariable Optional<User> user) throws IdNotFoundException 
	{
		return service.findByUser(user.orElseThrow(() -> new IdNotFoundException("User with id returned by context path was not found")));
	}
	
	@PreAuthorize("isAuthenticated()")
	@PutMapping({"{ticket}/paid"})
	public ResponseEntity<Ticket> makeTicketPaid(
			@PathVariable Optional<Ticket> ticket) throws IdNotFoundException {
		return ResponseEntity.ok(service.save(
				ticket.map(t -> {
					t.setPaid(true);
					return t;
				})
				.orElseThrow(() -> new IdNotFoundException("Ticket with id returned by context path was not found"))
				));
	}
	
	public void addTicketsToCurrentUser(List<Ticket> tickets) {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		tickets.stream()
			.forEach(ticket -> {
				Optional.ofNullable(ticket.getOwner()).ifPresent(e -> {throw new ResponseStatusException(HttpStatus.BAD_REQUEST);});
				ticket.setOwner(currentUser);
				service.save(ticket);
			});
	}
	
	public List<Ticket> generateEmptyTickets() {
		
		List<Ticket> emptyTickets = new ArrayList<>();
		
		for (int rowNum = 1; rowNum <= MAX_GENERATED_ROW; rowNum++) {
			for (int cellNum = 1; cellNum <= MAX_GENERATED_CELL; cellNum++) {
				emptyTickets.add(Ticket.builder()
						.rowNum(rowNum)
						.cellNum(cellNum)
						.price(new BigDecimal(TICKET_PRICE))
						.build());
			}
		}
		
		return emptyTickets;
	}
	
}
