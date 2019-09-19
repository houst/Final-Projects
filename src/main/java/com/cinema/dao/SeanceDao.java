package com.cinema.dao;

import com.cinema.entity.Seance;
import com.cinema.entity.Ticket;

public interface SeanceDao {

	Seance findByTicket(Ticket ticket);

}
