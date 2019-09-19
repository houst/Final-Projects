package com.cinema.dao;

import java.util.List;

import com.cinema.entity.User;

public interface UserDao {
	
	User create(User user);

	User findByEmail(String email);

	List<User> findAllUsers(int page, int size);
	
	long findCount();
    
//	void update(User user);
    
//	void delete(int id);
	
}
