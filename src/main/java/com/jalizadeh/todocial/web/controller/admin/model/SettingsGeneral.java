package com.jalizadeh.todocial.web.controller.admin.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

/**
 * Used for two purposes:
 * 1. As an Entity
 * 2. As a binding object in Admin controllers
 */

@NoArgsConstructor
@Data
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
	
}
