package com.codecrafter.hitect;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Hitech project REST API Documentation",
				description = "Hitech application REST API Documentation",
				version="v1",
				contact = @Contact(
						name = "Codecrafter",
						email = "info@code-crafter.in",
						url = "https://code-crafter.in"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Hitech application REST API Documentation",
				url = "https://code-crafter.in"
		)
)
@EnableCaching
public class HitechApplication {

	public static void main(String[] args) {
		SpringApplication.run(HitechApplication.class, args);
	}

}
