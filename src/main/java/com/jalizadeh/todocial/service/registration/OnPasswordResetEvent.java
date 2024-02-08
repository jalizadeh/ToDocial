package com.jalizadeh.todocial.service.registration;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.jalizadeh.todocial.model.user.PasswordResetToken;

public class OnPasswordResetEvent extends ApplicationEvent{

	private String appUrl;	
	private Locale locale;
	private PasswordResetToken prt;
	
	public OnPasswordResetEvent(PasswordResetToken prt, String appUrl, Locale locale) {
		super(prt);
		
		this.appUrl = appUrl;
		this.locale = locale;
		this.prt = prt;
	}

	
	public PasswordResetToken getPrt() {
		return prt;
	}

	public void setPrt(PasswordResetToken prt) {
		this.prt = prt;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}
