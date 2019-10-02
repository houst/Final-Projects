package com.cinema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinema.entity.Ticket;
import com.cinema.entity.User;
import com.cinema.exception.IdNotFoundException;
import com.cinema.repository.TicketRepository;

import lombok.NonNull;

@Service
public class TicketService {
	
	@Autowired
	TicketRepository repository;
	
	public Ticket save(@NonNull Ticket ticket) {
		return repository.save(ticket);
	}
	
	public Ticket findById(int id) throws IdNotFoundException {
		return repository.findById((long) id).orElseThrow(() -> new IdNotFoundException("There is no ticket with returned id from context path"));
	}

	public List<Ticket> findByUser(@NonNull User user) {
		return repository.findByOwner(user);
	}
	
}
