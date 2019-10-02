package com.cinema.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MovieDto {
	
	@NotBlank(message = "Title may not be blank")
	private String title;
	
	@Range(min = 1)
    private int duration;
    
    @NotBlank(message = "Description may not be blank")
    private String description;
    
}
