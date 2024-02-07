package com.jalizadeh.todocial.exception;

public class EmailExistsException extends Throwable {

	private static final long serialVersionUID = 1L;

	public EmailExistsException() {
		super();
	}

	public EmailExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EmailExistsException(String message, Throwable cause) {
		super(message, cause);
	}
	
    public EmailExistsException(final String message) {
        super(message);
    }

	public EmailExistsException(Throwable cause) {
		super(cause);
	}

}