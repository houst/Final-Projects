package com.cinema.dao;

import java.io.Closeable;
import java.util.List;

import com.cinema.entity.Movie;
import com.cinema.entity.Seance;
import com.cinema.entity.Ticket;

public interface SeanceDao extends Closeable {

	Seance findByTicket(Ticket ticket);
	
	List<Seance> findByMovie(Movie movie);

	Seance create(Seance newSeance);

	List<Seance> findAll(int page, int size);

	long findCount();

	Seance findById(long id);

	Seance update(Seance seance); 

}
