package com.jalizadeh.todocial.web.registration;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.jalizadeh.todocial.web.model.User;

public class OnRegistrationCompleteEvent extends ApplicationEvent{

	private String appUrl;	
	private Locale locale;
	private User user;
	
	public OnRegistrationCompleteEvent(User user, String appUrl, Locale locale) {
		super(user);
		
		this.appUrl = appUrl;
		this.locale = locale;
		this.user = user;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public User getUser() {
		return user;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
