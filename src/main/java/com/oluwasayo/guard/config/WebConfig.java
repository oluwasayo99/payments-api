package com.oluwasayo.guard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final PerformanceAuditInterceptor performanceAuditInterceptor;

    public WebConfig(PerformanceAuditInterceptor performanceAuditInterceptor) {
        this.performanceAuditInterceptor = performanceAuditInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(performanceAuditInterceptor)
                .addPathPatterns("/guard/**"); // Intercept only API endpoints under /guard
    }
}
