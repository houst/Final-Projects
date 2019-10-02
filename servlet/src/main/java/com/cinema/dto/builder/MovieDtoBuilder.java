package com.cinema.dto.builder;

import com.cinema.dto.MovieDto;

public class MovieDtoBuilder {
	
	private String title;

    private int duration;
    
    private String description;

	public MovieDtoBuilder title(String title) {
		this.title = title;
		return this;
	}

	public MovieDtoBuilder duration(int duration) {
		this.duration = duration;
		return this;
	}

	public MovieDtoBuilder description(String description) {
		this.description = description;
		return this;
	}
	
	public MovieDto build() {
		return new MovieDto(title, duration, description);
	}
    
}
