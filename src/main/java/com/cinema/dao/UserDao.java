package com.cinema.dao;

import java.sql.SQLException;

import com.cinema.entity.User;

public interface UserDao {
	
	User create(User user) throws SQLException;

	User findByEmail(String email) throws SQLException;
    
//	void update(User user);
    
//	void delete(int id);
	
}
