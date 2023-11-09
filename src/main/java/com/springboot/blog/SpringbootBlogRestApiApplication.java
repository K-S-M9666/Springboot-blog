package com.springboot.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring boot blog app rest Apis",
				description = "Spring boot blog app rest APIs documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Surya",
						email = "surya@gmail.com",
						url ="https://www.google.com"
						),
				license = @License(
						name ="Apache 2.0",
						url="https://www.javaguide.net/license"
						)
				),
		 externalDocs = @ExternalDocumentation(
				 description ="Spring boot Blog App Documentation",
				 url = "https://github.com/K-S-M9666/Springboot-blog"
				 )
		)

public class SpringbootBlogRestApiApplication {

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}

}
