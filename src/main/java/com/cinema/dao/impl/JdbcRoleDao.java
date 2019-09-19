package com.cinema.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cinema.dao.RoleDao;
import com.cinema.entity.Role;
import com.cinema.entity.User;
import com.cinema.exception.RuntimeSQLException;

public class JdbcRoleDao implements RoleDao {
	
	private Connection connection;

	JdbcRoleDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Role> findByUser(User user) {
		
		String sql = "SELECT authorities FROM user_role WHERE user_id = ?";
		
		List<Role> roles = new ArrayList<>();
		try (PreparedStatement pstmt = connection.prepareStatement(sql);) {
			pstmt.setLong(1, user.getId());
            try (ResultSet resultSet = pstmt.executeQuery();) {
                while (resultSet.next()) {
                	roles.add(Role.valueOf(resultSet.getString(1)));
                }
            }
        } catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
		return roles;
	}

	@Override
	public void create(User user) {
		String insertRole = "INSERT INTO user_role "
				+ "(user_id, authorities) "
				+ "values (?, ?)";
		
		String truncate = "DELETE FROM user_role "
				+ "WHERE user_id = ?";
		
		try (
				PreparedStatement pstmtTruncate = connection.prepareStatement(truncate);
				PreparedStatement pstmtRole = connection.prepareStatement(insertRole);) {
			
			pstmtTruncate.setLong(1, user.getId());
			pstmtTruncate.execute();
			
		    for (Role role : user.getAuthorities()) {
		        pstmtRole.setLong(1, user.getId());
		        pstmtRole.setString(2, role.getAuthority());
		        pstmtRole.addBatch();
		    }
		    
		    try {
		    	pstmtRole.executeBatch();
		    } catch (SQLException e) {
		    	throw new SQLException("Saving roles failed, no rows affected.", e);
			}
		    
		    
		} catch (SQLException e) {
			throw new RuntimeSQLException(e);
		}
	}

	@Override
	public void update(User user) {
		create(user);
	}
	
}