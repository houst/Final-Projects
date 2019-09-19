package com.cinema.dao;

import java.util.List;

import com.cinema.entity.Movie;
import com.cinema.entity.Seance;
import com.cinema.entity.User;

public interface MovieDao {
	
	Movie findBySeance(Seance seance);
	
	List<Movie> findByUser(User user);
	
}
