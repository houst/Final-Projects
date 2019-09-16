package com.cinema.controller.command;

import javax.servlet.http.HttpServletRequest;

import com.cinema.entity.User;
import com.cinema.service.UserService;

import exception.EmailExistsException;
import exception.EmailNotFoundException;

public class UserCommand implements Command {
	
	private UserService service = new UserService();
	
	@Override
	public String execute(HttpServletRequest request) {
		return null;
	}
	
	public User addNewUser(User newUser) throws EmailExistsException {
		return service.create(newUser);
	}
	
	public User getUserByEmail(String email) throws EmailNotFoundException {
		return service.findByEmail(email);
	}
	
}
