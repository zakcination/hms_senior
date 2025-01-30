package com.hms.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;
import java.util.stream.Collectors;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Housing Management System API",
                version = "1.0",
                description = """
                        REST API documentation for Housing Management System
                        
                        API Versions:
                        - V1: Current stable version
                        - V2: Beta version with new features
                        """,
                contact = @Contact(
                        name = "HMS Support",
                        email = "support@hms.com",
                        url = "https://www.hms.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Development Server"
                ),
                @Server(
                        url = "https://api.hms.com",
                        description = "Production Server"
                )
        },
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer sortPathsCustomizer() {
        return openApi -> {
            Paths paths = new Paths();
            openApi.getPaths().entrySet().stream()
                    .sorted(Comparator.comparing(e -> e.getKey().toLowerCase()))
                    .forEach(e -> paths.addPathItem(e.getKey(), e.getValue()));
            openApi.setPaths(paths);
        };
    }
} 