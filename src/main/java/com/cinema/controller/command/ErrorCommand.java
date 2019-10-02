package com.cinema.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) {
		return CommandUtility.error(HttpServletResponse.SC_NOT_FOUND);
	}

}
