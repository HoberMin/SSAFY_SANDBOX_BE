package com.example.skilltestingbe.global.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {

	private final String frontUrl;
	private final String backPattern;

	@Autowired
	public WebMvcConfigure(@Value("${FRONT.URL}") String frontUrl, @Value("${BACK.PATTERN}") String backPattern) {
		this.frontUrl = frontUrl;
		this.backPattern = backPattern;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping(backPattern)
				.allowedOrigins(frontUrl)
				.allowedMethods(
						HttpMethod.GET.name(),
						HttpMethod.POST.name(),
						HttpMethod.PATCH.name(),
						HttpMethod.DELETE.name(),
						HttpMethod.OPTIONS.name())
				.allowCredentials(true)
				.allowedHeaders("*")
				.exposedHeaders("*");
	}
}
