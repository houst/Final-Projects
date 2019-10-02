package com.cinema.dto.builder;

import com.cinema.dto.SeanceDto;

public class SeanceDtoBuilder {
	
	private String begin;
	
	private String movieTitle;

	public SeanceDtoBuilder begin(String begin) {
		this.begin = begin;
		return this;
	}

	public SeanceDtoBuilder movieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
		return this;
	}
	
	public SeanceDto build() {
		return new SeanceDto(begin, movieTitle);
	}
	
}
