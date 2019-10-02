package com.cinema.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.cinema.dao.SeanceDao;
import com.cinema.entity.Movie;
import com.cinema.entity.Seance;
import com.cinema.entity.Ticket;
import com.cinema.exception.RuntimeSQLException;

@SuppressWarnings("resource")
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
	
	@Override
	public List<Seance> findByMovie(Movie movie) {
		String sql = "SELECT * FROM seance WHERE movie_id = ?";
		
		List<Seance> seances = new ArrayList<>();
		
		try (PreparedStatement psmt = connection.prepareStatement(sql);) {
			
			psmt.setLong(1, movie.getId());
            try (ResultSet resultSet = psmt.executeQuery();) {
                while (resultSet.next()) {
                	Seance seance = extractFromResultSet(resultSet);
                	seance.setMovie(movie);
                	seance.setTickets(new JdbcTicketDao(connection).findBySeance(seance));
                	seances.add(seance);
                }
            }
            
		} catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
		return seances;
	}

	@Override
	public List<Seance> findAll(int page, int size) {
		String sql = "SELECT * FROM seance LIMIT ?, ?"; 
		List<Seance> seances = new ArrayList<>();
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
			pstmt.setInt(1, (page - 1) * size);
			pstmt.setInt(2, size);
            try (ResultSet resultSet = pstmt.executeQuery();) {
                while (resultSet.next()) {
                	Seance seance = extractFromResultSet(resultSet); 
                	seance.setMovie(new JdbcMovieDao(connection).findBySeance(seance));
                	seance.setTickets(new JdbcTicketDao(connection).findBySeance(seance));
                	seances.add(seance);
                }
            }
            
        } catch (SQLException e) {
        	throw new RuntimeSQLException(e);
		}
		return seances;
	}

	@Override
	public long findCount() {
		String sql = "SELECT COUNT(*) FROM seance";
		long count = 0;
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
			
            try (ResultSet resultSet = pstmt.executeQuery();) {
                if (resultSet.next()) {
                	count = resultSet.getLong(1);
                }
            }
            
        } catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
		
		return count;
	}

	@Override
	public Seance findById(long id) {
		String sql = "SELECT * FROM seance "
				+ "WHERE id = ?";
		
		Seance seance = null;
		
		try ( PreparedStatement pstmt = connection.prepareStatement(sql);) {
			
			pstmt.setLong(1, id);
            try (ResultSet resultSet = pstmt.executeQuery();) {
                if (resultSet.next()) {
                	seance = extractFromResultSet(resultSet);
                }
            }
            
            if (seance == null) {
    			throw new SQLException("There is no seance with this id: " + id);
    		}
            
            seance.setMovie(new JdbcMovieDao(connection).findBySeance(seance));
            seance.setTickets(new JdbcTicketDao(connection).findBySeance(seance));
            
        } catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
		
		return seance;
	}

	@Override
	public Seance create(Seance seance) {
		String sql = "INSERT INTO seance "
				+ "(start_date_time, movie_id) "
				+ "VALUES (?, ?)";
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql, 
        		Statement.RETURN_GENERATED_KEYS);) {
			
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			
			pstmt.setTimestamp(1, Timestamp.valueOf(seance.getStartDateTime()));
			pstmt.setLong(2, seance.getMovie().getId());
			
		    int affectedRowsMovies = pstmt.executeUpdate();
		    
		    if (affectedRowsMovies == 0) {
		    	connection.rollback();
		        throw new SQLException("Creating seance failed, no rows affected.");
		    }
		
		    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
		        if (generatedKeys.next()) {
		        	seance.setId(generatedKeys.getLong(1));
		        }
		        else {
		        	connection.rollback();
		            throw new SQLException("Creating seance failed, no ID obtained.");
		        }
		    }
		    
		    JdbcTicketDao jdbcTicketDao = new JdbcTicketDao(connection);
		    List<Ticket> tickets = seance.getTickets();
		    for (Ticket ticket : tickets) {
		    	ticket.setSeance(seance);
		    	jdbcTicketDao.create(ticket);    	
		    }
		    
		    connection.commit();
		    
		} catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
		
		return seance;
	}

	@Override
	public Seance update(Seance seance) {
		// TODO Auto-generated method stub
		return null;
	}

	private Seance extractFromResultSet(ResultSet rs) throws SQLException{
        return Seance.builder()
    			.id(rs.getLong("id"))
    			.startDateTime(rs.getTimestamp("start_date_time").toLocalDateTime())
    			.build();
    }
	
	public void close() {
        try {
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
    }

}
