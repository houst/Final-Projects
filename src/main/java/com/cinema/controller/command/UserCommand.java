package com.cinema.controller.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cinema.dto.UserDto;
import com.cinema.entity.Role;
import com.cinema.entity.User;
import com.cinema.exception.EmailExistsException;
import com.cinema.exception.IdNotFoundException;
import com.cinema.service.UserService;

public class UserCommand implements Command {
	
	private UserService service = new UserService();
	
	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_SIZE = 10;
	
	@Override
	public String execute(HttpServletRequest request) {
		String method = request.getMethod();
		
		if ("GET".equals(method)) {
			return doGet(request);
		}
		
		if ("POST".equals(method)) {
			return doPost(request);
		}
		
		if ("PUT".equals(method)) {
			return doPut(request);
		}
		
		if ("DELETE".equals(method)) {
			return doDelete(request);
		}
		
		return CommandUtility.error(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}
	
	private String doGet(HttpServletRequest request) {
		if (!CommandUtility.checkUserIsGranted(request)) {
			return CommandUtility.error(HttpServletResponse.SC_NOT_FOUND);
		}
		
		String path = request.getRequestURI();
		String regexUserList = ".*/user/?$";
		String regexUserEdit = ".*/[0-9]+/edit/?$";
		
		if (path.matches(regexUserEdit)) {
			return doGetUserEditPage(request);
		}
		
		if (path.matches(regexUserList)) {
			return doGetUserListPage(request);
		}
		
		return CommandUtility.error(HttpServletResponse.SC_NOT_FOUND);
	}
	
	private String doGetUserEditPage(HttpServletRequest request) {
		int userIdFromPath = Integer.parseInt(request.getRequestURI().split("/")[3]);
		User user = null;
		try {
			user = service.findById(userIdFromPath);
		} catch (IdNotFoundException e) {
			return CommandUtility.error(HttpServletResponse.SC_NOT_FOUND);
		}
		
		request.setAttribute("user", user);
		request.setAttribute("roles", Role.values());
		
		return "/WEB-INF/user-edit.jsp";
	}
	
	private String doGetUserListPage(HttpServletRequest request) {
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
		
		long elementsCount = service.findCount();
		request.setAttribute("elements", service.findAll(page, size));
		request.setAttribute("elementsCount", elementsCount);
		request.setAttribute("page", page);
		request.setAttribute("size", size);
		request.setAttribute("pagesCount", (int) Math.ceil(elementsCount * 1.0 / size));
		
		return "/WEB-INF/user-list.jsp";
	}
	
	private String doPost(HttpServletRequest request) {
		
		UserDto userDto = UserDto.builder()
				.email(request.getParameter("email"))
				.password(request.getParameter("password"))
				.matchingPassword(request.getParameter("matchingPassword"))
				.username(request.getParameter("username"))
				.tel(request.getParameter("tel"))
				.build();
		
		if (userDto.getPassword() == null ||
				!userDto.getPassword().equals(userDto.getMatchingPassword()) ||
				!userDto.getEmail().matches(RegExConsts.REGEX_EMAIL) ||
				!userDto.getUsername().matches(RegExConsts.REGEX_NAME)) {
			
			return CommandUtility.error(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		try {
			service.create(convertToEntity(userDto));
		} catch (EmailExistsException e) {
			return CommandUtility.error(HttpServletResponse.SC_CONFLICT);
		}
		
		return CommandUtility.json("{ \"created\" : true}");
	}
	
	private String doPut(HttpServletRequest request) {
		Map<String, String> data = CommandUtility.getParameterMap(request);
		User user = User.builder()
				.id(Long.parseLong(CommandUtility.decodeValue(data.get("userId"))))
				.email(CommandUtility.decodeValue(data.get("email")))
				.username(CommandUtility.decodeValue(data.get("name")))
				.tel(CommandUtility.decodeValue(data.get("number")))
				.build();
		
		Set<String> roles = Arrays.stream(Role.values())
				.map(Role::name).collect(Collectors.toSet());
		
		user.setAuthorities(new ArrayList<Role>());
		
		for (String key : data.keySet()) {
			if (roles.contains(key)) {
				user.getAuthorities().add(Role.valueOf(key));
			}
		}
		
		try {
			service.update(user);
		} catch (IdNotFoundException e) {
			CommandUtility.error(HttpServletResponse.SC_NOT_FOUND);
		}
		return CommandUtility.json("{ \"updated\" : true}");
	}
	
	private String doDelete(HttpServletRequest request) {
		return CommandUtility.error(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}
	
//	private UserDto convertToDto(User user) {
//		return UserDto.builder()
//				.email(user.getEmail())
//				.password(user.getPassword())
//				.matchingPassword(user.getPassword())
//				.username(user.getUsername())
//				.tel(user.getTel())
//				.build();
//	}
	
	private User convertToEntity(UserDto userDto) {
		return User.builder()
    			.email(userDto.getEmail())
    			.password(userDto.getPassword())
    			.authorities(Arrays.asList(Role.USER))
    			.username(userDto.getUsername())
    			.tel(userDto.getTel())
    			.accountNonExpired(true)
    			.accountNonLocked(true)
    			.credentialsNonExpired(true)
    			.enabled(true)
    			.build();
	}
	
}
