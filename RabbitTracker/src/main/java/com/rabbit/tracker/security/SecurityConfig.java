package com.rabbit.tracker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// Setting up authentication
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Setting cors
		http.cors().configurationSource(this.corsConfigurationSource());
		http.authorizeRequests().antMatchers("/*", "/static/**").permitAll();
		// Authentication
		http.authorizeRequests().anyRequest().authenticated();
		// Ignore CSRF to use cookies
		http.csrf().ignoringAntMatchers("/api/**");

		http.oauth2ResourceServer().jwt();

		return http.build();
	}

	private CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
		corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
		corsConfiguration.addExposedHeader("Authorization");
		corsConfiguration.addAllowedOrigin("http://localhost:8081");
		corsConfiguration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
		corsSource.registerCorsConfiguration("/**", corsConfiguration);
		return corsSource;
	}
}
