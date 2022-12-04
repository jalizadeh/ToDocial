package com.jalizadeh.todocial;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;

import com.jalizadeh.todocial.web.service.storage.StorageProperties;
import com.jalizadeh.todocial.web.service.storage.StorageService;
import com.jalizadeh.todocial.web.system.AppLocaleResolver;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ToDocial {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ToDocial.class);
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
