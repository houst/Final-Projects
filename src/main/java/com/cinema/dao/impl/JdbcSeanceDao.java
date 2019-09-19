package com.cinema.dao.impl;

import java.sql.*;
import java.util.ArrayList;

import com.cinema.dao.SeanceDao;
import com.cinema.entity.Seance;
import com.cinema.entity.Ticket;
import com.cinema.exception.RuntimeSQLException;

public class JdbcSeanceDao implements SeanceDao {
	
	private Connection connection;
	
	JdbcSeanceDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Seance findByTicket(Ticket ticket) {
		
		String sql = "SELECT s.* FROM seance s "
				+ "INNER JOIN ticket t ON t.seance_id = s.id "
				+ "WHERE t.id = ?";
		Seance seance = null;
		
		try ( PreparedStatement pstmt = connection.prepareStatement(sql);) {
			
			pstmt.setLong(1, ticket.getId());
            try (ResultSet resultSet = pstmt.executeQuery();) {
                if (resultSet.next()) {
                	seance = extractFromResultSet(resultSet);
                }
            }
            
            if (seance == null) {
    			throw new SQLException("There is no seance with this ticket id: " + ticket.getId());
    		}
            
            seance.setMovie(new JdbcMovieDao(connection).findBySeance(seance));
            seance.setTickets(new ArrayList<Ticket>());
            
        } catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
		
		return seance;
	}
	
	private Seance extractFromResultSet(ResultSet rs) throws SQLException{
        return Seance.builder()
    			.id(rs.getLong("id"))
    			.startDateTime(rs.getTimestamp("start_date_time").toLocalDateTime())
    			.build();
    }
	
	public Seance create(Seance seance) throws SQLException {
		String sql = "INSERT INTO seance "
				+ "(startDateTime, movie_id) "
				+ "VALUES (?, ?)";
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
			pstmt.setTimestamp(1, Timestamp.valueOf(seance.getStartDateTime()));
			pstmt.setLong(2, seance.getMovie().getId());
		}
		return seance;
	}

}
