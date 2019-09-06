package com.cinema.entity;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@NotBlank(message = "Title may not be blank")
	@Column(unique = true)
	private String title;
	
	@Range(min = 1)
	private int duration;
	
	@Lob 
	@Column(name="descr", length=1024)
	private String description;
	
	@OneToMany(mappedBy = "movie")
	private List<Seance> seances;
	
}
