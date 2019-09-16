package com.cinema.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cinema.dao.SeanceDao;
import com.cinema.dao.TicketDao;
import com.cinema.entity.Ticket;
import com.cinema.entity.User;

public class JdbcTicketDao implements TicketDao {

	private Connection connection;
	
	JdbcTicketDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Ticket> findByUser(User user) throws SQLException {
		
		String sql = "SELECT * FROM ticket WHERE user_id = ?";
		
		List<Ticket> tickets = new ArrayList<>();
		try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
			pstmt.setLong(1, user.getId());
            try (ResultSet resultSet = pstmt.executeQuery();) {
                while (resultSet.next()) {
                	Ticket ticket = extractFromResultSet(resultSet);
                	ticket.setOwner(user);
                	tickets.add(ticket);
                }
            }
        }
		
		SeanceDao seanceDao = new JdbcSeanceDao(connection);
		for (Ticket ticket : tickets) {
			ticket.setSeance(seanceDao.findByTicket(ticket));
		}
		
		return tickets;
	}
	
	private Ticket extractFromResultSet(ResultSet rs) throws SQLException{
        return Ticket.builder()
    			.id(rs.getLong("id"))
    			.rowNum(rs.getInt("row_num"))
    			.cellNum(rs.getInt("cell_num"))
    			.price(rs.getBigDecimal("price"))
    			.paid(rs.getBoolean("is_paid"))
    			.expired(rs.getBoolean("is_expired"))
    			.build();
    }
	
	public void close()  {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	
}