package com.cinema.dto;

import javax.validation.constraints.NotBlank;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SeanceDto {
	
	@NotBlank(message = "Starting date and time must exists")
	private String begin;
	
	@NotBlank(message = "Movie title must exists")
	private String movieTitle;
	
}
