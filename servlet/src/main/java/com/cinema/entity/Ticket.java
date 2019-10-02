package com.cinema.entity;

import java.math.BigDecimal;

import com.cinema.entity.builder.TicketBuilder;

public class Ticket implements IBillingItem {
	
	private long id;
	
	private int rowNum;
	
	private int cellNum;
	
	private User owner;
	
	private Seance seance;

	private BigDecimal price;
	
	private boolean paid;
	
	private boolean expired;
	
	public Ticket() {}

	public Ticket(long id, int rowNum, int cellNum, User owner, Seance seance, BigDecimal price, boolean paid, boolean expired) {
		this.id = id;
		this.rowNum = rowNum;
		this.cellNum = cellNum;
		this.owner = owner;
		this.seance = seance;
		this.price = price;
		this.paid = paid;
		this.expired = expired;
	}

	public long getId() {
		return id;
	}

	public int getRowNum() {
		return rowNum;
	}

	public int getCellNum() {
		return cellNum;
	}

	public User getOwner() {
		return owner;
	}

	public Seance getSeance() {
		return seance;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public boolean isPaid() {
		return paid;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public void setCellNum(int cellNum) {
		this.cellNum = cellNum;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public void setSeance(Seance seance) {
		this.seance = seance;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	@Override
	public String getItemInfo() {
		return "Ticket[id=" + id + ", " 
				+ "seance=" + seance.getStartDateTime() + ", "
				+ "row=" + rowNum + ", "
				+ "cell=" + cellNum + "]";
	}
	
	public static TicketBuilder builder() {
		return new TicketBuilder();
	}
	
}
