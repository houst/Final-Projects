package com.cinema.exception;

public class AuthorityNotFoundException extends Exception {

	private static final long serialVersionUID = 2567058974958400965L;

	public AuthorityNotFoundException(String authority) {
		super(String.format("Authority \"%s\" not found", authority));
	}
	
}
