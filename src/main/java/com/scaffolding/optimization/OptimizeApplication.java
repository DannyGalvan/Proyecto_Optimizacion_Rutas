package com.scaffolding.optimization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@SpringBootApplication
@EnableJpaRepositories
public class OptimizeApplication {
	public static void main(String[] args) {
		SpringApplication.run(OptimizeApplication.class, args);
	}

	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
				.components(new io.swagger.v3.oas.models.Components()
						.addSecuritySchemes("bearerAuth",
								new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
										.bearerFormat("JWT")))
				.info(new Info()
						.title("API chatbot Misol")
						.version("1.0.0")
						.description("This is a simple API chatbot Misol")
						.termsOfService("https://swagger.io/terms")
						.license(new License().name("Apache 2.0").url("https://springdoc.org")));
	}
}
