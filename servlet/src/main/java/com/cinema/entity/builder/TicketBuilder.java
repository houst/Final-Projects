package com.cinema.entity.builder;

import java.math.BigDecimal;

import com.cinema.entity.Seance;
import com.cinema.entity.Ticket;
import com.cinema.entity.User;

public class TicketBuilder {
	
	private long id;
	
	private int rowNum;
	
	private int cellNum;
	
	private User owner;
	
	private Seance seance;

	private BigDecimal price;
	
	private boolean paid;
	
	private boolean expired;

	public TicketBuilder id(long id) {
		this.id = id;
		return this;
	}

	public TicketBuilder rowNum(int rowNum) {
		this.rowNum = rowNum;
		return this;
	}

	public TicketBuilder cellNum(int cellNum) {
		this.cellNum = cellNum;
		return this;
	}

	public TicketBuilder owner(User owner) {
		this.owner = owner;
		return this;
	}

	public TicketBuilder seance(Seance seance) {
		this.seance = seance;
		return this;
	}

	public TicketBuilder price(BigDecimal price) {
		this.price = price;
		return this;
	}

	public TicketBuilder paid(boolean paid) {
		this.paid = paid;
		return this;
	}

	public TicketBuilder expired(boolean expired) {
		this.expired = expired;
		return this;
	}
	
	public Ticket build() {
		return new Ticket(id, rowNum, cellNum, owner, seance, price, paid, expired);
	}
	
}
