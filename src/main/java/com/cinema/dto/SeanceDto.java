package com.cinema.dto;

import com.cinema.dto.builder.SeanceDtoBuilder;

public class SeanceDto {
	
	private String begin;
	
	private String movieTitle;

	public SeanceDto() {}

	public SeanceDto(String begin, String movieTitle) {
		this.begin = begin;
		this.movieTitle = movieTitle;
	}

	public String getBegin() {
		return begin;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	
	public static SeanceDtoBuilder builder() {
		return new SeanceDtoBuilder();
	}
	
}
