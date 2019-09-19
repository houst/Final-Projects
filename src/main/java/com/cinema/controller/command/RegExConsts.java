package com.cinema.controller.command;

public interface RegExConsts {
	
	
	String REGEX_NAME = "^[A-Z][a-z]{1,20}$" + "|"	
				+ "^[А-ЩЬЮЯҐІЇЄ][а-щьюяґіїє']{1,20}$"; 
	String REGEX_EMAIL = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@"
	  		+ "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

}
