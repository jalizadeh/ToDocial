package com.jalizadeh.springboot.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.spel.spi.EvaluationContextExtension;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.jalizadeh.springboot.web.model.FlashMessage;
import com.jalizadeh.springboot.web.service.IUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private IUserService iUserService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth
			.userDetailsService(iUserService)
			.passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers(
						"/favicon.ico",
						"/js/**",
						"/WEB-INF/**",
						"/webjars/**",
						"/registration-confirm",
						"/signup",
						"/forgot-password",
						"/reset-password"
						).permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest()
					.hasAnyRole("USER", "ADMIN")
			.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.successHandler(loginSuccessHandler())
				.failureHandler(loginFailureHandler())
			.and()
			.logout()
				.permitAll()
				.logoutUrl("/logout");
				//.logoutSuccessUrl("/login");

	}
	
	public AuthenticationSuccessHandler loginSuccessHandler() {
        return (request, response, authentication) ->
        	response.sendRedirect("/");
    }

    public AuthenticationFailureHandler loginFailureHandler() {
        return (request, response, exception) -> {
        	//System.err.println("SecurityConfiguration > loginFailureHandler > " + exception.getMessage());
        	
        	if(exception.getMessage().contains("User is disabled")) {
        		request.getSession().setAttribute("flash",
            		new FlashMessage("Please confirm your email to activate your account", 
            				FlashMessage.Status.danger));
        	} else if (exception.getMessage().contains("Bad credentials")) {
        		request.getSession().setAttribute("flash",
                		new FlashMessage("Username or Password is not correct.", 
                				FlashMessage.Status.danger));
        	} else {
        		request.getSession().setAttribute("flash",
            		new FlashMessage(exception.getMessage(), FlashMessage.Status.danger));
        	}
        	
            response.sendRedirect("/login");
        };
    }
    
    
    
    @Bean
	public EvaluationContextExtension secutiryExtension() {
		return new EvaluationContextExtension() {
			
			@Override
			public String getExtensionId() {
				return "security";
			}

			@Override
			public Object getRootObject() {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				return new SecurityExpressionRoot(authentication) {};
			}
		};
	}
	
    
	
    /*
	@Autowired
	public void configureGloablSecurity(AuthenticationManagerBuilder auth) throws Exception{
		//auth.inMemoryAuthentication().withUser("javad").password("12345").roles("USER", "ADMIN");
		
		auth.inMemoryAuthentication()
        	.withUser("javad").password("{noop}12345").roles("USER", "ADMIN");
	}
	*/
	
}
