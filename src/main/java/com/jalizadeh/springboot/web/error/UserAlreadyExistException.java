package com.jalizadeh.springboot.web.error;

public final class UserAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = -1512760961177065831L;

	
	public UserAlreadyExistException() {
		super();
	}


	public UserAlreadyExistException(final String message, Throwable cause) {
		super(message, cause);
	}

	public UserAlreadyExistException(final String message) {
		super(message);
	}

	public UserAlreadyExistException(final Throwable cause) {
		super(cause);
	}

}
