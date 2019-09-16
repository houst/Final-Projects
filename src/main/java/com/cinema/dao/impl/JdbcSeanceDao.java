package com.cinema.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cinema.dao.SeanceDao;
import com.cinema.entity.Seance;
import com.cinema.entity.Ticket;

public class JdbcSeanceDao implements SeanceDao {
	
	private Connection connection;
	
	JdbcSeanceDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Seance findByTicket(Ticket ticket) throws SQLException {
		
		String sql = "SELECT s.* FROM seance s "
				+ "INNER JOIN ticket t ON t.seance_id = s.id "
				+ "WHERE t.id = ?";
		Seance seance = null;
		
		try ( PreparedStatement pstmt = connection.prepareStatement(sql);) {
			
			pstmt.setLong(1, ticket.getId());
            try (ResultSet resultSet = pstmt.executeQuery();) {
                if (resultSet.next()) {
                	seance = new Seance();
                	seance.setId(resultSet.getLong("id"));
                	seance.setStartDateTime(resultSet.getTimestamp("start_date_time").toLocalDateTime());
                }
            }
            
            if (seance == null) {
    			throw new SQLException("There is no seance with this ticket id: " + ticket.getId());
    		}
            
            seance.setMovie(new JdbcMovieDao(connection).findBySeance(seance));
            seance.setTickets(new ArrayList<Ticket>());
            
        }
		
		return seance;
	}

}
