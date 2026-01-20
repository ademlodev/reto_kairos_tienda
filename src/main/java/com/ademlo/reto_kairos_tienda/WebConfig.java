package com.ademlo.reto_kairos_tienda;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración Web para servir el contrato OpenAPI estático
 * desde {@code classpath:/openapi/} bajo la ruta /openapi/**.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
				.addResourceHandler("/openapi/**")
				.addResourceLocations("classpath:/openapi/");
	}
}

