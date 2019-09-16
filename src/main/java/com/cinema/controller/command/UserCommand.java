package com.cinema.controller.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cinema.entity.User;
import com.cinema.service.UserService;

import exception.EmailExistsException;
import exception.EmailNotFoundException;

public class UserCommand implements Command {
	
	private UserService service = new UserService();
	
	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_SIZE = 10;
	
	@Override
	public String execute(HttpServletRequest request) {
		String method = request.getMethod();
		
		if ("GET".equals(method)) {
			Integer page = null;
			Integer size = null;

			try {
				page = Integer.parseInt(request.getParameter("page"));
			} catch (NumberFormatException e) {
				page = DEFAULT_PAGE;
			}
			
			try {
				size = Integer.parseInt(request.getParameter("size"));
			} catch (NumberFormatException e) {
				size = DEFAULT_SIZE;
			}
			
			long elementsCount = getUsersCount();
			request.setAttribute("elements", getAllUsersPaginated(page, size));
			request.setAttribute("elementsCount", elementsCount);
			request.setAttribute("page", page);
			request.setAttribute("size", size);
			request.setAttribute("pagesCount", (int) Math.ceil(elementsCount * 1.0 / size));
			
			return "/WEB-INF/user-list.jsp";
		}
		
		return "error:" + HttpServletResponse.SC_METHOD_NOT_ALLOWED;
	}
	
	public User addNewUser(User newUser) throws EmailExistsException {
		return service.create(newUser);
	}
	
	public User getUserByEmail(String email) throws EmailNotFoundException {
		return service.findByEmail(email);
	}
	
	public List<User> getAllUsersPaginated(int page, int size) {
		return service.findAllUsers(page, size);
	}
	
	public long getUsersCount() {
		return service.findCount();
	}
	
}
