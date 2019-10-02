package com.cinema.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.cinema.dao.UserDao;
import com.cinema.entity.Movie;
import com.cinema.entity.Ticket;
import com.cinema.entity.User;
import com.cinema.exception.RuntimeSQLException;

@SuppressWarnings("resource")
public class JdbcUserDao implements UserDao {
	
	private Connection connection;
	
	JdbcUserDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public User findById(long id) {
		String sql = "SELECT * FROM user WHERE id = ?";
		
		User user = null;
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
			
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			
			pstmt.setLong(1, id);
            try (ResultSet resultSet = pstmt.executeQuery();) {
                if (resultSet.next()) {
                	user = extractFromResultSet(resultSet);
                }
            }
            
            if (user == null) {
    			throw new SQLException("There is no user with this id: " + id);
    		}
            
            user.setAuthorities(new JdbcRoleDao(connection).findByUser(user));
            user.setTickets(new JdbcTicketDao(connection).findByUser(user));
            user.setWatchedMovies(new JdbcMovieDao(connection).findByUser(user));
            
        } catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
		
		return user;
	}

	@Override
	public User findByEmail(String email) {
		String sql = "SELECT * FROM user WHERE email = ?";
		
		User user = null;
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
			
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			
			pstmt.setString(1, email);
            try (ResultSet resultSet = pstmt.executeQuery();) {
                if (resultSet.next()) {
                	user = extractFromResultSet(resultSet);
                }
            }
            
            if (user == null) {
    			throw new SQLException("There is no user with this email: " + email);
    		}
            
            user.setAuthorities(new JdbcRoleDao(connection).findByUser(user));
            user.setTickets(new JdbcTicketDao(connection).findByUser(user));
            user.setWatchedMovies(new JdbcMovieDao(connection).findByUser(user));
            
        } catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
		
		return user;
	}
	
	@Override
	public User findByTicket(Ticket ticket) {
		String sql = "SELECT u.* FROM user u "
				+ "INNER JOIN ticket t ON t.user_id = u.id "
				+ "WHERE t.id = ?";
		User user = null;
		
		try ( PreparedStatement pstmt = connection.prepareStatement(sql);) {
			
			pstmt.setLong(1, ticket.getId());
            try (ResultSet resultSet = pstmt.executeQuery();) {
                if (resultSet.next()) {
                	user = extractFromResultSet(resultSet);
                }
            }
            
            if (user == null) {
    			throw new SQLException("There is no user with this ticket id: " + ticket.getId());
    		}
            
            user.setWatchedMovies(new ArrayList<Movie>());
            user.setTickets(new ArrayList<Ticket>());
            
        } catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
		
		return user;
	}

	@Override
	public long findCount() {
		String sql = "SELECT COUNT(*) FROM user";
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
	public List<User> findAllUsers(int page, int size) {
		String sql = "SELECT * FROM user LIMIT ?, ?"; 
		List<User> users = new ArrayList<>();
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
			pstmt.setInt(1, (page - 1) * size);
			pstmt.setInt(2, size);
            try (ResultSet resultSet = pstmt.executeQuery();) {
                while (resultSet.next()) {
                	User user = extractFromResultSet(resultSet);
                	user.setAuthorities(new JdbcRoleDao(connection).findByUser(user));
                	users.add(user);
                }
            }
            
        } catch (SQLException e) {
        	throw new RuntimeSQLException(e);
		}
		
		return users;
	}
	
	@Override
	public User create(User user) {
		String insertUser = "INSERT INTO user "
				+ "(email, password, username, tel, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?)";
		
		
		
		try (PreparedStatement pstmtUser = connection.prepareStatement(insertUser, 
                		Statement.RETURN_GENERATED_KEYS);) {
			
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			
			pstmtUser.setString(1, user.getEmail());
			pstmtUser.setString(2, user.getPassword());
			pstmtUser.setString(3, user.getUsername());
			pstmtUser.setString(4, user.getTel());
			pstmtUser.setBoolean(5, user.isAccountNonExpired());
			pstmtUser.setBoolean(6, user.isAccountNonLocked());
			pstmtUser.setBoolean(7, user.isCredentialsNonExpired());
			pstmtUser.setBoolean(8, user.isEnabled());
			
            int affectedRowsUser = pstmtUser.executeUpdate();
            
            if (affectedRowsUser == 0) {
            	connection.rollback();
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmtUser.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                }
                else {
                	connection.rollback();
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            
            try {
            	new JdbcRoleDao(connection).create(user);
            } catch (RuntimeSQLException e) {
            	connection.rollback();
				throw new SQLException("Creating roles failed, no rows affected", e);
			}
            
            connection.commit();
            
        } catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
		
		return user;
		
	}
	
	public User update(User user) {
		String sql = "UPDATE user SET "
				+ "email = ?, username = ?, tel = ? "
				+ "WHERE id = ?";
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
			
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			
			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, user.getUsername());
			pstmt.setString(3, user.getTel());
			pstmt.setLong(4, user.getId());
			pstmt.execute();
            
			try {
            	new JdbcRoleDao(connection).update(user);
            } catch (RuntimeSQLException e) {
            	connection.rollback();
				throw new SQLException("Creating roles failed, no rows affected", e);
			}
            
            connection.commit();
			
        } catch (SQLException e) {
        	throw new RuntimeSQLException(e);
		}
		
		return findById(user.getId());
	}
	
	private User extractFromResultSet(ResultSet rs) throws SQLException{
        return User.builder()
    			.id(rs.getLong("id"))
    			.email(rs.getString("email"))
    			.password(rs.getString("password"))
    			.username(rs.getString("username"))
    			.tel(rs.getString("tel"))
    			.accountNonExpired(rs.getBoolean("is_account_non_expired"))
    			.accountNonLocked(rs.getBoolean("is_account_non_locked"))
    			.credentialsNonExpired(rs.getBoolean("is_credentials_non_expired"))
    			.enabled(rs.getBoolean("is_enabled"))
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
