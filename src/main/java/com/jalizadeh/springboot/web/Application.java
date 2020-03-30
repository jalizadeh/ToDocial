package com.jalizadeh.springboot.web;

import java.util.Properties;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

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
        application.run(args);
	}
	
	
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			//storageService.deleteAll();
			storageService.init();
		};
	}

}
