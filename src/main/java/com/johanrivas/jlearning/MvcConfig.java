package com.johanrivas.jlearning;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	// public void addViewControllers(ViewControllerRegistry registry) {
	// registry.addViewController("/error_403").setViewName("error_403");
	// }

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// // Can just allow `methods` that you need.
		// registry.addMapping("/api/v1/login").allowedMethods("*").allowedOrigins("*");
		// registry.addMapping("/api/v1/login").allowedOrigins("http://localhost:3000")
		// .allowedMethods("POST", "GET", "DELETE", "PUT").allowedHeaders("header1",
		// "header2", "header3")
		// .exposedHeaders("header1", "header2").allowCredentials(true).maxAge(3600);
		registry.addMapping("/**").allowedMethods("*").allowedOrigins("*");
	}
}
