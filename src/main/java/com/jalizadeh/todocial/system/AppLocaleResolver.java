package com.jalizadeh.todocial.system;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.jalizadeh.todocial.controller.admin.model.SettingsGeneralConfig;

@Configuration
public class AppLocaleResolver extends SessionLocaleResolver {

	@Autowired
	private SettingsGeneralConfig settings;

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
 	    return stringToLocale(settings.getLanguage());
    }
    
    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        super.setLocale(request, response, locale);
        SessionLocaleResolver slr = new SessionLocaleResolver();
 	    slr.setDefaultLocale(stringToLocale(settings.getLanguage()));
    }
    
    
    public Locale stringToLocale(String s) {    	
        return new Locale(s.split("_")[0],s.split("_")[1]);
    }
} 
