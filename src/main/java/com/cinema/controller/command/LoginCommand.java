package com.cinema.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cinema.entity.Role;
import com.cinema.entity.User;
import com.cinema.exception.EmailNotFoundException;
import com.cinema.service.UserService;

public class LoginCommand implements Command {
	
	private UserService userService = new UserService();
	
	@Override
	public String execute(HttpServletRequest request) {
		String method = request.getMethod();
		
		if ("POST".equals(method)) {
			final String email = request.getParameter("email");
			final String password = request.getParameter("password");
			
			if( email == null || email.equals("") || password == null || password.equals("")  ){
				return CommandUtility.error(HttpServletResponse.SC_BAD_REQUEST);
	        }
			
			User loggedUser = null;
			try {
				loggedUser = userService.findByEmail(email);
			} catch (EmailNotFoundException e) {
				return CommandUtility.error(HttpServletResponse.SC_BAD_REQUEST); 
			}
			
			final boolean isAuthenticated = password.equals(loggedUser.getPassword());
			
			if (!isAuthenticated) {
				return CommandUtility.error(HttpServletResponse.SC_BAD_REQUEST);
			}

			final boolean isAdmin = loggedUser.getAuthorities().stream().anyMatch(role -> role.equals(Role.ADMIN));

			HttpSession session = request.getSession();
			session.setAttribute("loggedUser", loggedUser);
			session.setAttribute("isAuthenticated", isAuthenticated);
			session.setAttribute("isAdmin", isAdmin);
			
			
			return CommandUtility.json("{\"loggedUser\" : true}");
		}
		
		return CommandUtility.error(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

}
