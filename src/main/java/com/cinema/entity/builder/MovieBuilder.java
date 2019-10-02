package com.cinema.entity.builder;

import java.util.List;

import com.cinema.entity.Movie;
import com.cinema.entity.Seance;

public class MovieBuilder {
	
	private long id;
	
	private String title;
	
	private int duration;
	
	private String description;
	
	private List<Seance> seances;

	public MovieBuilder id(long id) {
		this.id = id;
		return this;
	}

	public MovieBuilder title(String title) {
		this.title = title;
		return this;
	}

	public MovieBuilder duration(int duration) {
		this.duration = duration;
		return this;
	}

	public MovieBuilder description(String description) {
		this.description = description;
		return this;
	}

	public MovieBuilder seances(List<Seance> seances) {
		this.seances = seances;
		return this;
	}
	
	public Movie build() {
		return new Movie(id, title, duration, description, seances);
	}
	
}
