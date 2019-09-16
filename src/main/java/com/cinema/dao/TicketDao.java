package com.cinema.dao;

import java.sql.SQLException;
import java.util.List;

import com.cinema.entity.Ticket;
import com.cinema.entity.User;

public interface TicketDao {
	
	List<Ticket> findByUser(User user) throws SQLException;
	
}
