package com.cinema.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.cinema.dao.SeanceDao;
import com.cinema.dao.TicketDao;
import com.cinema.dao.UserDao;
import com.cinema.entity.Seance;
import com.cinema.entity.Ticket;
import com.cinema.entity.User;
import com.cinema.exception.RuntimeSQLException;

@SuppressWarnings("resource")
public class JdbcTicketDao implements TicketDao {

	private Connection connection;
	
	JdbcTicketDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Ticket> findByUser(User user) {
		
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
        } catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
		
		SeanceDao seanceDao = new JdbcSeanceDao(connection);
		for (Ticket ticket : tickets) {
			ticket.setSeance(seanceDao.findByTicket(ticket));
		}
		
		return tickets;
	}
	
	@Override
	public List<Ticket> findBySeance(Seance seance) {
		String sql = "SELECT * FROM ticket WHERE seance_id = ?";
		
		List<Ticket> tickets = new ArrayList<>();
		try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
			pstmt.setLong(1, seance.getId());
            try (ResultSet resultSet = pstmt.executeQuery();) {
                while (resultSet.next()) {
                	Ticket ticket = extractFromResultSet(resultSet);
                	ticket.setSeance(seance);
                	tickets.add(ticket);
                }
            }
            
        } catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
		
		
		UserDao userDao = new JdbcUserDao(connection);
		for (Ticket ticket : tickets) {
			try {
				ticket.setOwner(userDao.findByTicket(ticket));
			} catch (RuntimeSQLException e) {
				ticket.setOwner(null);
			}
		}
		
		return tickets;
	}

	@Override
	public Ticket create(Ticket ticket) {
		String sql = "INSERT INTO ticket "
				+ "(row_num, cell_num, seance_id, is_paid, price, is_expired) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql, 
        		Statement.RETURN_GENERATED_KEYS);) {
			
			pstmt.setInt(1, ticket.getRowNum());
			pstmt.setInt(2, ticket.getCellNum());
			pstmt.setLong(3, ticket.getSeance().getId());
			pstmt.setBoolean(4, ticket.isPaid());
			pstmt.setBigDecimal(5, ticket.getPrice());
			pstmt.setBoolean(6, ticket.isExpired());
			
		    int affectedRowsMovies = pstmt.executeUpdate();
		    
		    if (affectedRowsMovies == 0) {
		        throw new SQLException("Creating ticket failed, no rows affected.");
		    }
		
		    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
		        if (generatedKeys.next()) {
		        	ticket.setId(generatedKeys.getLong(1));
		        }
		        else {
		            throw new SQLException("Creating ticket failed, no ID obtained.");
		        }
		    }
		    
		} catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
		return ticket;
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
            throw new RuntimeSQLException(e);
        }
    }
	
}