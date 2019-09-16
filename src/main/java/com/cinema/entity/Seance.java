package com.cinema.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.cinema.entity.builder.SeanceBuilder;

public class Seance {
	
	private long id;
	
	private LocalDateTime startDateTime;
	
	private List<Ticket> tickets;
	
	private Movie movie;

	public Seance() {}

	public Seance(long id, LocalDateTime startDateTime, List<Ticket> tickets, Movie movie) {
		super();
		this.id = id;
		this.startDateTime = startDateTime;
		this.tickets = tickets;
		this.movie = movie;
	}

	public long getId() {
		return id;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
	public static SeanceBuilder builder() {
		return new SeanceBuilder();
	}
	
}
