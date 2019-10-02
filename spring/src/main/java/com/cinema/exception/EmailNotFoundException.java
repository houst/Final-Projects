package com.cinema.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class EmailNotFoundException extends UsernameNotFoundException {

	private static final long serialVersionUID = 8121010802123369837L;

	public EmailNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmailNotFoundException(String message) {
		super(message);
	}

}
