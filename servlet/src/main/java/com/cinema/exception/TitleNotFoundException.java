package com.cinema.exception;

public class TitleNotFoundException extends Exception {

	private static final long serialVersionUID = 684500656090448114L;

	public TitleNotFoundException() {
		super();
	}

	public TitleNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TitleNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public TitleNotFoundException(String message) {
		super(message);
	}

	public TitleNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
