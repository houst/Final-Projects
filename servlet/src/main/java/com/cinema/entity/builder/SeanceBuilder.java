package com.cinema.entity.builder;

import java.time.LocalDateTime;
import java.util.List;

import com.cinema.entity.Movie;
import com.cinema.entity.Seance;
import com.cinema.entity.Ticket;

public class SeanceBuilder {
	
	private long id;
	
	private LocalDateTime startDateTime;
	
	private List<Ticket> tickets;
	
	private Movie movie;

	public SeanceBuilder id(long id) {
		this.id = id;
		return this;
	}

	public SeanceBuilder startDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
		return this;
	}

	public SeanceBuilder tickets(List<Ticket> tickets) {
		this.tickets = tickets;
		return this;
	}

	public SeanceBuilder movie(Movie movie) {
		this.movie = movie;
		return this;
	}
	
	public Seance build() {
		return new Seance(id, startDateTime, tickets, movie);
	}
	
}
