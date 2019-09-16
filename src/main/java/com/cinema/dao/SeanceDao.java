package com.cinema.dao;

import java.sql.SQLException;

import com.cinema.entity.Seance;
import com.cinema.entity.Ticket;

public interface SeanceDao {

	Seance findByTicket(Ticket ticket) throws SQLException;

}
