package com.cinema.exception;

public class TitleExistsException extends Exception {

	private static final long serialVersionUID = -1143575863930782255L;

	public TitleExistsException() {
		super();
	}

	public TitleExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TitleExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public TitleExistsException(String message) {
		super(message);
	}

	public TitleExistsException(Throwable cause) {
		super(cause);
	}
	
}
