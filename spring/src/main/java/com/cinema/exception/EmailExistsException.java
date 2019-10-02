package com.cinema.exception;

public class EmailExistsException extends Exception {

	private static final long serialVersionUID = -942786373271784323L;

	public EmailExistsException() {
		super();
	}

	public EmailExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EmailExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmailExistsException(String message) {
		super(message);
	}

	public EmailExistsException(Throwable cause) {
		super(cause);
	}
	
}
