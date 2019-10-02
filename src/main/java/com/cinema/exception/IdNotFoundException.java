package com.cinema.exception;

public class IdNotFoundException extends Exception {

	private static final long serialVersionUID = 684500656090448114L;

	public IdNotFoundException() {
		super();
	}

	public IdNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IdNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public IdNotFoundException(String message) {
		super(message);
	}

	public IdNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
