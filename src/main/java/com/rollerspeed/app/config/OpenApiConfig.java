package com.rollerspeed.app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Roller Speed API",
                version = "v1",
                description = "Sistema de gestión para la escuela de patinaje Roller Speed"
        )
)
public class OpenApiConfig {
}
