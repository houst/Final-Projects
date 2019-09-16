package com.cinema.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.cinema.dao.UserDao;
import com.cinema.entity.Role;
import com.cinema.entity.User;

public class JdbcUserDao implements UserDao {
	
	private Connection connection;
	
	JdbcUserDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public User findByEmail(String email) throws SQLException {
		String sql = "SELECT * FROM user WHERE email = ?";
		
		User user = null;
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
			
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
            
        }
		
		return user;
	}
	
	@Override
	public long findCount() throws SQLException {
		String sql = "SELECT COUNT(*) FROM user";
		long count = 0;
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
			
            try (ResultSet resultSet = pstmt.executeQuery();) {
                if (resultSet.next()) {
                	count = resultSet.getLong(1);
                }
            }
            
        }
		
		return count;
	}
 
	@Override
	public List<User> findAllUsers(int page, int size) throws SQLException {
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
            
        }
		
		return users;
	}
	
	@Override
	public User create(User user) throws SQLException {
		String insertUser = "INSERT INTO user "
				+ "(email, password, username, tel, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?)";
		
		String insertRole = "INSERT INTO user_role "
				+ "(user_id, authorities) "
				+ "values (?, ?)";
		
		try (PreparedStatement pstmtUser = connection.prepareStatement(insertUser, 
                		Statement.RETURN_GENERATED_KEYS);
				PreparedStatement pstmtRole = connection.prepareStatement(insertRole);) {
			
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			
			// Save user
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
//            	connection.close();
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmtUser.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                }
                else {
                	connection.rollback();
//                	connection.close();
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            
            // Save roles
            for (Role role : user.getAuthorities()) {
	            pstmtRole.setLong(1, user.getId());
	            pstmtRole.setString(2, role.getAuthority());
	            pstmtRole.addBatch();
            }
            
            try {
            	pstmtRole.executeBatch();
            } catch (SQLException e) {
            	connection.rollback();
//            	connection.close();
            	throw new SQLException("Saving roles failed, no rows affected.");
			}
            
            connection.commit();
//            connection.close();
            
        }
		
		return user;
		
	}
	
	//update
//	try (BasicDataSource dataSource = DataBaseUtility.getDataSource(); 
//            Connection connection = dataSource.getConnection();
//            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM account");)
//    {
//System.out.println("The Connection Object is of Class: "+connection.getClass());
//        try (ResultSet resultSet = pstmt.executeQuery();)
//        {
//            while (resultSet.next())
//            {
//                System.out.println(resultSet.getString(1) + "," + resultSet.getString(2) + "," + resultSet.getString(3));
//            }
//        }
//        catch (Exception e)
//        {
//            connection.rollback();
//            e.printStackTrace();
//        }
//    }
	
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
	
	public void close()  {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
