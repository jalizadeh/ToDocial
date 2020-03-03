package com.jalizadeh.springboot.web.error;

@SuppressWarnings("serial")
public class EmailExistsException extends Throwable {

	public EmailExistsException() {
		super();
	}

	public EmailExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
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