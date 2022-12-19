package com.jalizadeh.todocial.system.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * I prefer not to use it with my current security configuration
 * I control access to my URLs or methods via {@link SecurityConfig} class
 */
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration{

	/**
	 * This class can be used as a source of rules for controlling
	 * access to methods on any desired class
	 */
	@Override
	public MethodSecurityMetadataSource methodSecurityMetadataSource() {

		Map<String, List<ConfigAttribute>> methodMap = new HashMap<>();
		methodMap.put("com.jalizadeh.todocial.web.controller.admin.DashboardController.ShowAdminPanel*", 
				SecurityConfig.createList("ROLE_ADMIN"));
		return new MapBasedMethodSecurityMetadataSource(methodMap);
	}
	
	
}
