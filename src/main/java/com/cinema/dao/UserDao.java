package com.cinema.dao;

import java.io.Closeable;
import java.util.List;

import com.cinema.entity.Ticket;
import com.cinema.entity.User;

public interface UserDao extends Closeable {
	
	User create(User user);

	User findById(long id);
	
	User findByEmail(String email);
	
	User findByTicket(Ticket ticket);

	List<User> findAllUsers(int page, int size);
	
	long findCount();

	User update(User user);
	
}
