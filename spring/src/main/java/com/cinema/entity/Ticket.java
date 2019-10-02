package com.cinema.entity;

import java.math.BigDecimal;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket implements IBillingItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	private int rowNum;
	
	private int cellNum;
	
	@ManyToOne
    @JoinColumn(name="user_id", nullable=true)
	private User owner;
	
	@ManyToOne
    @JoinColumn(name="seance_id", nullable=false)
	private Seance seance;

	private BigDecimal price;
	
	@Column(name="is_paid")
	private boolean paid;
	
	@Column(name="is_expired")
	private boolean expired;

	@Override
	public String getItemInfo() {
		return "Ticket[id=" + id + ", " 
				+ "seance=" + seance.getStartDateTime() + ", "
				+ "row=" + rowNum + ", "
				+ "cell=" + cellNum + "]";
	}
	
}
