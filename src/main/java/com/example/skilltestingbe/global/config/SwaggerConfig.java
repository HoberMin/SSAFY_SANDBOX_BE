package com.example.skilltestingbe.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "SSAFY SANDBOX API SPECIFICATION",
                description = "Specification for service SSAFY SANDBOX",
                version = "v1.0.0"),
        servers = @Server(url = "https://api-server.store", description = "HTTPS server")
)
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi v1OpenApi() {
        return GroupedOpenApi.builder()
                .group("SSAFY SANDBOX SPEC V1")
                .pathsToMatch("/**")
                .build();
    }
}