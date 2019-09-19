package com.cinema.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) {
		return CommandUtility.generateError(HttpServletResponse.SC_NOT_FOUND);
	}

}
