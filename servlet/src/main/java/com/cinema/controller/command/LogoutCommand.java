package com.cinema.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("loggedUser", null);
		session.setAttribute("isAuthenticated", false);
		session.setAttribute("isAdmin", false);
		String locale = (String) (request.getSession().getAttribute("locale") != null ? request.getSession().getAttribute("locale") : "");
		return "redirect:/" + locale + "?loggedout=true";
	}

}
