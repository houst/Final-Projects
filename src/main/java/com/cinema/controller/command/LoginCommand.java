package com.cinema.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cinema.entity.Role;
import com.cinema.entity.User;

import exception.EmailNotFoundException;

public class LoginCommand implements Command {
	
	UserCommand userRestCommand = new UserCommand();
	
	@Override
	public String execute(HttpServletRequest request) {
		String method = request.getMethod();
		
		if ("POST".equals(method)) {
			final String email = request.getParameter("email");
			final String password = request.getParameter("password");
			
			if( email == null || email.equals("") || password == null || password.equals("")  ){
				return "error:" + HttpServletResponse.SC_BAD_REQUEST;
	        }
			
			User loggedUser = null;
			try {
				loggedUser = userRestCommand.getUserByEmail(email);
			} catch (EmailNotFoundException e) {
				return "error:" + HttpServletResponse.SC_BAD_REQUEST; 
			}
			
			final boolean isAuthenticated = password.equals(loggedUser.getPassword());
			
			if (!isAuthenticated) {
				return "error:" + HttpServletResponse.SC_BAD_REQUEST;
			}

			final boolean isAdmin = loggedUser.getAuthorities().stream().anyMatch(role -> role.equals(Role.ADMIN));

			HttpSession session = request.getSession();
			session.setAttribute("loggedUser", loggedUser);
			session.setAttribute("isAuthenticated", isAuthenticated);
			session.setAttribute("isAdmin", isAdmin);
			
			
			return "json:{\"loggedUser\" : true}";
		}
		
		return "error:" + HttpServletResponse.SC_METHOD_NOT_ALLOWED;
	}

}
