package com.gemini.store;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Crud Api Book Store Application" ,version = "2.7.1" , description = "Crud Api Using Spring Boot") )
public class BookstoreGeminiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreGeminiApplication.class, args);
		
	}

}
