package com.jalizadeh.springboot.web.controller.admin.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * This object holds the latest settings, from startup and while run time
 * If other controllers need to get the latest settings, they can access
 * this object.
 */

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
	
	@Value("${language:English}") 
	private String language;
	

	public SettingsGeneralConfig() {
		super();
	}

	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

	public String getSiteName() {
		return siteName;
	}

	public String getSiteDescription() {
		return siteDescription;
	}

	public boolean isAnyoneCanRegister() {
		return anyoneCanRegister;
	}

	public String getDefaultRole() {
		return defaultRole;
	}

	public String getServerLocalTime() {
		return serverLocalTime;
	}

	public String getDateStructure() {
		return dateStructure;
	}

	public String getTimeStructure() {
		return timeStructure;
	}

	public String getLanguage() {
		return language;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public void setSiteDescription(String siteDescription) {
		this.siteDescription = siteDescription;
	}

	public void setAnyoneCanRegister(boolean anyoneCanRegister) {
		this.anyoneCanRegister = anyoneCanRegister;
	}

	public void setDefaultRole(String defaultRole) {
		this.defaultRole = defaultRole;
	}

	public void setServerLocalTime(String serverLocalTime) {
		this.serverLocalTime = serverLocalTime;
	}

	public void setDateStructure(String dateStructure) {
		this.dateStructure = dateStructure;
	}

	public void setTimeStructure(String timeStructure) {
		this.timeStructure = timeStructure;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getFooterCopyright() {
		return footerCopyright;
	}

	public void setFooterCopyright(String footerCopyright) {
		this.footerCopyright = footerCopyright;
	}

	

}
