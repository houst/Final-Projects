package com.cinema.entity;

import java.util.List;

import com.cinema.entity.builder.MovieBuilder;

public class Movie {
	
	private long id;
	
	private String title;
	
	private int duration;
	
	private String description;
	
	private List<Seance> seances;

	public Movie() {}

	public Movie(long id, String title, int duration, String description, List<Seance> seances) {
		this.id = id;
		this.title = title;
		this.duration = duration;
		this.description = description;
		this.seances = seances;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public int getDuration() {
		return duration;
	}

	public String getDescription() {
		return description;
	}

	public List<Seance> getSeances() {
		return seances;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSeances(List<Seance> seances) {
		this.seances = seances;
	}
	
	public static MovieBuilder builder() {
		return new MovieBuilder();
	}
	
}
