package com.cinema.dao;

import java.util.List;

import com.cinema.entity.User;

public interface UserDao {
	
	User create(User user);

	User findById(long id);
	
	User findByEmail(String email);

	List<User> findAllUsers(int page, int size);
	
	long findCount();

	User update(User user);
	
}
