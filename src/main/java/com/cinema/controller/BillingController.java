package com.cinema.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cinema.entity.Ticket;
import com.cinema.entity.User;
import com.cinema.exception.IdNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/bill")
public class BillingController {
	
	@Autowired
	TicketController ticketController;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/tickets/pay")
	public ResponseEntity<String> payTicketsBillForCurrentUser() throws IdNotFoundException {
		log.info("IN billingController - payTicketsBillForCurrentUser() : Start paying for tickets...");
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Ticket> ticketsOfCurrentUser = ticketController.getTicketsByUser(Optional.of(user));
		
		ticketsOfCurrentUser.stream().forEach(ticket -> {
			try {
				ticketController.makeTicketPaid(Optional.of(ticket));
				log.info("{} : Success", ticket.getItemInfo());
			} catch (IdNotFoundException e) {
				log.error("User was not found!");
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
		});
		
		log.info("IN billingController - payTicketsBillForCurrentUser() : Completed successfully!");
		return ResponseEntity.ok().build();
	}
	
}
