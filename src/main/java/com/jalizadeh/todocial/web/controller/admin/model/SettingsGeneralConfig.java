package com.jalizadeh.todocial.web.controller.admin.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This object holds the latest settings, from startup and during runtime
 * if other controllers need to get the latest settings, they can access
 * this object.
 */

@NoArgsConstructor
@Data
@Configuration
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
@PropertySource("classpath:settings-default.properties")
public class SettingsGeneralConfig {
	
	@Value("${site.title:ToDocial}")
	private String siteName;
	
	@Value("${site.footer.copyright:© 2020 ToDocial}")
	private String footerCopyright;
	
	@Value("${site.description:}")
	private String siteDescription;
	
	@Value("${system.anyoneCanRegister:true}") 
	private boolean anyoneCanRegister;
	
	@Value("${system.role.default:ROLE_USER}") 
	private String defaultRole;
	
	@Value("${system.localTime:+0 UTC}") 
	private String serverLocalTime;
	
	@Value("${system.date:long}") 
	private String dateStructure;
	
	@Value("${system.time:short}") 
	private String timeStructure;
	
	@Value("${system.language:en_US}") 
	private String language;
	
}
