package com.jalizadeh.springboot.web.controller.admin.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

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
@PropertySource("classpath:settings-default.properties")
public class SettingsGeneralConfig {
	
	@Value("${site.name:ToDocial}")
	private String siteName;
	
	@Value("${footer.copyright:© 2020 ToDocial}")
	private String footerCopyright;
	
	@Value("${site.description:}")
	private String siteDescription;
	
	@Value("${anyone.can.register:true}") 
	private boolean anyoneCanRegister;
	
	@Value("${default.role:ROLE_USER}") 
	private String defaultRole;
	
	@Value("${server.local.time:+0 UTC}") 
	private String serverLocalTime;
	
	@Value("${date.structure:long}") 
	private String dateStructure;
	
	@Value("${time.structure:short}") 
	private String timeStructure;
	
	@Value("${language:en_US}") 
	private String language;
	

	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
