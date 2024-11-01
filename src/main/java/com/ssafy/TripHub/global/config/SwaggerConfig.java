package com.ssafy.TripHub.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TripHub API Documentation")
                        .version("v1.0")
                        .description("API documentation for TripHub application. This API allows you to interact with the Trip Information system.")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                );
    }
}
