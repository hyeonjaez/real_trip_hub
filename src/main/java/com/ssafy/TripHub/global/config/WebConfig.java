package com.ssafy.TripHub.global.config;


import com.ssafy.TripHub.global.auth.AuthenticatedUserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticatedUserArgumentResolver authenticatedUserArgumentResolver;

    @Autowired
    public WebConfig(AuthenticatedUserArgumentResolver authenticatedUserArgumentResolver) {
        this.authenticatedUserArgumentResolver = authenticatedUserArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticatedUserArgumentResolver);
    }
}
