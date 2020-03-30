package com.jalizadeh.springboot.web.controller.admin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

/**
 * Used for two purposes:
 * 1. As an Entity
 * 2. As a binding object in Admin controllers
 */

@Entity
public class SettingsGeneral {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotEmpty(message="Site name can not be empty")
	private String siteName;
	
	private String siteDescription;
	
	private String footerCopyright;
	
	private boolean anyoneCanRegister;
	
	private String defaultRole;
	
	private String serverLocalTime;
	
	private String dateStructure;
	
	private String timeStructure;
	
	private String language;

	public SettingsGeneral() {
		super();
	}

	public Long getId() {
		return id;
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

	public void setId(Long id) {
		this.id = id;
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
