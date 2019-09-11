package com.cinema.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seance")
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id"
)
public class Seance {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@Column(name = "start_date_time")
	private LocalDateTime startDateTime;
	
	@OneToMany(mappedBy = "seance", cascade = {CascadeType.PERSIST})
	private List<Ticket> tickets;
	
	@ManyToOne
    @JoinColumn(name="movie_id", nullable=false)
	private Movie movie;
	
	@Override
	public String toString() {
		return startDateTime.toString();
	}
	
}
