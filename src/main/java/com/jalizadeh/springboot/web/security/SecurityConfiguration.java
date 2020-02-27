package com.jalizadeh.springboot.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	public void configureGloablSecurity(AuthenticationManagerBuilder auth) throws Exception{
		//auth.inMemoryAuthentication().withUser("javad").password("12345").roles("USER", "ADMIN");
		
		auth.inMemoryAuthentication()
        	.withUser("javad").password("{noop}12345").roles("USER", "ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/login").permitAll()
				.antMatchers("/", "/*todo*/**")
				.access("hasRole('USER')").and()
				.formLogin();
	}
}
