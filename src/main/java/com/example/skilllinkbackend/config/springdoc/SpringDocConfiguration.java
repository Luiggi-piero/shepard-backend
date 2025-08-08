package com.example.skilllinkbackend.config.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("Shepard API")
                        .description("API Rest de la aplicación Shepard que contiene las funcionalidades CRUD de tipos de habitaciones, habitaciones, departamentos, reservas, además gestiona la autenticación de usuarios")
                        .contact(new Contact()
                                .name("Equipo Backend")
                                .email("luiggiyantas@gmail.com")
                        )
                        .license(new License()
                                .name("Licencia MIT")
                                .url("http://shepard/api/licencia"))
                );
    }
}
