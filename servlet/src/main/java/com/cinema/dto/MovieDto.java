package com.cinema.dto;

import com.cinema.dto.builder.MovieDtoBuilder;

public class MovieDto {
	
	private String title;

    private int duration;
    
    private String description;

	public MovieDto() {}

	public MovieDto(String title, int duration, String description) {
		super();
		this.title = title;
		this.duration = duration;
		this.description = description;
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

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static MovieDtoBuilder builder() {
		return new MovieDtoBuilder();
	}
    
}
