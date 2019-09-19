package com.cinema.dao;

import java.util.List;

import com.cinema.entity.Role;
import com.cinema.entity.User;

public interface RoleDao {
	
	List<Role> findByUser(User user);
	
	void create(User user);
	
	void update(User user);
	
}
