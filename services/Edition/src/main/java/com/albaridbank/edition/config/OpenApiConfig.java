package com.albaridbank.edition.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for OpenAPI (Swagger) documentation.
 * This class sets up the OpenAPI documentation for the AlbaridBank Edition API.
 *
 * @author Mohamed Amine Eddafir
 */
@Configuration
public class OpenApiConfig {

    /**
     * Creates a custom OpenAPI bean with security schemes and API information.
     *
     * @return an OpenAPI object configured with security schemes and API information
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("AlbaridBank Edition API")
                        .description("API du microservice Edition pour la génération de rapports Excel")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Mohamed Amine Eddafir")
                                .email("eddafir.mohamed.amine01@gmail.com")
                                .url("https://github.com/mohamedAmineEddafir")));
//                        .license(new License()
//                                .name("AlbaridBank License")
//                                .url("https://www.albaridbank.ma")));
    }
}