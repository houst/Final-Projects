package com.cinema.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.cinema.entity.Ticket;

public class TicketService {
	
	private static final int MAX_GENERATED_ROW = 3;
	
	private static final int MAX_GENERATED_CELL = 6;

	private static final String TICKET_PRICE = "10";
	
	public List<Ticket> generateAvailableTickets() {
		
		List<Ticket> availableTickets = new ArrayList<>();
		
		for (int rowNum = 1; rowNum <= MAX_GENERATED_ROW; rowNum++) {
			for (int cellNum = 1; cellNum <= MAX_GENERATED_CELL; cellNum++) {
				availableTickets.add(Ticket.builder()
						.rowNum(rowNum)
						.cellNum(cellNum)
						.price(new BigDecimal(TICKET_PRICE))
						.build());
			}
		}
		
		return availableTickets;
	}
	
}
