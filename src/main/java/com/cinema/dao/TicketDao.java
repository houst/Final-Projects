package com.cinema.dao;

import java.io.Closeable;
import java.util.List;

import com.cinema.entity.Seance;
import com.cinema.entity.Ticket;
import com.cinema.entity.User;

public interface TicketDao extends Closeable {
	
	List<Ticket> findByUser(User user);
	
	List<Ticket> findBySeance(Seance seance);
	
	Ticket create(Ticket ticket);
	
}
