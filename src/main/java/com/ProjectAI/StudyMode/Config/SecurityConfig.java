package com.ProjectAI.StudyMode.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

/**
 * Minimal security configuration:
 * - CSRF disabled (for a simple JSON API used by a SPA)
 * - Uses the application's CorsConfigurationSource bean via Customizer.withDefaults()
 * - Permits all API endpoints for now (change to restrict later)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF (Crucial for non-browser clients or simple fetch requests)
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Enable CORS using the bean above
                .cors(cors -> cors.configure(http))

                // 3. Allow everything
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
