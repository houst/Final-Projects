package com.cinema.controller.command;

import javax.servlet.http.HttpServletRequest;

public class CommandUtility {
	
	private CommandUtility() {}
	
	static boolean checkUserIsLogged(HttpServletRequest request) {
        return request.getSession().getAttribute("loggedUser") != null;
    }
	
	static boolean checkUserIsGranted(HttpServletRequest request) {
        return checkUserIsLogged(request) ? (Boolean) request.getSession().getAttribute("isAdmin") : Boolean.FALSE;
    }
	
	static String generateError(int statusCode) {
		return "error:" + statusCode;
	}

}
