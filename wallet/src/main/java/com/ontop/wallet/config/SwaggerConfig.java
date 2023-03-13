package com.ontop.wallet.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi api() {
		return GroupedOpenApi.builder()
	              .group("public-api")
	              .pathsToMatch("/**")
	              .build();  
	}
	
	  @Bean
	  public OpenAPI springOpenAPI() {
	      return new OpenAPI()
	              .info(new Info().title("API Prueba OnTop - Wallet")
	              .description("Api rest para transaccion de dinero desde wallet a provedor externo")
	              .version("v1.0.0")
	              .license(new License().name("Apache 2.0").url("http://springdoc.org")))
	              .externalDocs(new ExternalDocumentation()
//	              .description("SpringShop Wiki Documentation")
//	              .url("https://springshop.wiki.github.org/docs")
	              );
	  }
}
