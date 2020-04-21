package com.jalizadeh.springboot.web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;

import com.jalizadeh.springboot.web.security.AppLocaleResolver;
import com.jalizadeh.springboot.web.service.storage.StorageProperties;
import com.jalizadeh.springboot.web.service.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Application {

	public static void main(String[] args) {
		
		
		SpringApplication application = new SpringApplication(Application.class);
		
		/*
        Properties properties = new Properties();
        properties.put("server.port", 9999);
        application.setDefaultProperties(properties);
		*/
		
		/*
		//generating test questions
		String x = "";
		for (int j = 1; j < 11; j++) {
			x = x + "<li class=\"list-group-item\">\n"  
					+ "<fieldset class=\"form-group\">\n"
					+"<p>?????</p>\n";
			for(int i=1; i<6 ; i++) {
				x = x +"<div class=\"form-check form-check-inline\">\n"+
							"<input class=\"form-check-input\" type=\"radio\" ";
				x = x + "name=\"q" + j + "\" id=\"q"+ j + "" + i + "\" value=\"" + i + "\" required>";
				x = x + "<label class=\"form-check-label\" for=\"q" + j + "\">" + i + "</label>";
				x = x + "</div>";
			}
			x = x + "</fieldset>\n</li>";
		}
		
		System.out.println(x);
		*/
		
		
		
        application.run(args);
	}
	
	
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			//storageService.deleteAll();
			storageService.init();
		};
	}


	@Bean
	public LocaleResolver localeResolver() {
	    return new AppLocaleResolver();
	}
	
	
}
