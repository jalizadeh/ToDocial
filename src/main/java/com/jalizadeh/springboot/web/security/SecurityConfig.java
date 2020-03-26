package com.jalizadeh.springboot.web.security;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.spel.spi.EvaluationContextExtension;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.google.common.collect.Lists;
import com.jalizadeh.springboot.web.model.FlashMessage;
import com.jalizadeh.springboot.web.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth
			.userDetailsService(userService)
			.passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	
	/*
	 * AntPathMatcher
	 * --------------------------
     * ? matches one character
     * * matches zero or more characters
     * ** matches zero or more 'directories' in a path
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers(
						"/static_res/**", //it is easier to permit all files in this folder
						"/WEB-INF/**",
						"/webjars/**",
						"/registration-confirm",
						"/login",
						"/signup",
						"/forgot-password",
						"/reset-password",
						"/error",
						"/",
						"/@*"
						).permitAll()
				

				
				/*
				 * Testing security expressions
				 * these expressions can be also used directly in JSPs
				 *
				.antMatchers("/secured")
					.access("hasRole('ADMIN')")
					.access("hasAuthority('ROLE_ADMIN')")
					.access("hasRole('ADMIN')")
					.hasIpAddress("192.168.1.0/24")
					.access("hasIpAddress('192.168.1.0/24')")
					.access("hasIpAddress('::1')")
					.access("isAnonymous()")
					.access("isAuthenticated()")
					.access("request.method == 'GET'")
					.access("request.method != 'PUT'")
					.not().access("hasIpAddress('::1')")
					.access("hasRole('ADMIN') and principal.username == 'user'")
				*/
				
				.antMatchers("/admin/**").hasAuthority("PRIVILEGE_WRITE") //no more need to check logged in user in controllers
				.anyRequest().authenticated()//.accessDecisionManager(unanimous())
			.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.successHandler(loginSuccessHandler())
				.failureHandler(loginFailureHandler())
			.and()
			.logout()
				.permitAll()
				.logoutUrl("/logout")
			.and()
			.rememberMe()
				/*
				 * Cookie-based
				 */ 
				.tokenValiditySeconds(604800) //one week
				.key("lssApplicationKey") //my own key to be used while hashing
				//.useSecureCookie(true) //enable cookie only in secured connection = https
				.rememberMeParameter("remember") //the element's name in login form
				
				
				
				/*
				 * Persistence-based
				 * The token is stored in DB
				 */
				//.tokenRepository(persistentTokenRepository())
			
			.and()
			.sessionManagement()
				.maximumSessions(1)
				.sessionRegistry(sessionRegistry())
				.and()
				.sessionFixation().none()
			
			.and()
			.csrf()
				.disable();

	}
	
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource);
		return jdbcTokenRepository;
	}
	
	
	public AuthenticationSuccessHandler loginSuccessHandler() {
        return (request, response, authentication) ->
        	response.sendRedirect("/");
    }

    public AuthenticationFailureHandler loginFailureHandler() {
        return (request, response, exception) -> {

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
	
    
    /**
     * Custom decision voters
     * You can define your own voter logic and use it as a rule
     * Like: A realtime user-lock
     * 		 which can store the locked user, and only let the users
     * 		 that are not locked [module 9 - L4]   
     */
    @Bean
    public AccessDecisionManager unanimous() {
    	List<AccessDecisionVoter<? extends Object>> decisionVoters = 
    			Lists.newArrayList(new RoleVoter(), new AuthenticatedVoter(), 
    					new WebExpressionVoter());
    	
    	return new UnanimousBased(decisionVoters); 
    }
    
	
    /*
	@Autowired
	public void configureGloablSecurity(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication()
        	.withUser("javad").password("{noop}12345").roles("ADMIN")
        	.and()
        	.withUser("user").password("{noop}pass").roles("USER");
	}
	*/

}
