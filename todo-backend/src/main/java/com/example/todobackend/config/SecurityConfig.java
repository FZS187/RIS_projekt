package com.example.todobackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CORS MORA biti PRVI
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Isključi CSRF za REST API
                .csrf(csrf -> csrf.disable())

                // Session management - IF_REQUIRED za session-based auth
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )

                // Authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Javni endpointi
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/auth/register").permitAll()
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/todos/categories").permitAll()  // ✅ DODAJ
                        .requestMatchers("/api/todos/priorities").permitAll()  // ✅ DODAJ
                        // Ostalo zahtijeva autentifikaciju
                        .anyRequest().authenticated()
                )

                // Isključi form login
                .formLogin(form -> form.disable())

                // Isključi HTTP Basic
                .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // ✅ EKSPLICITNO navedi origin (ne koristi * sa credentials)
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",
                "http://127.0.0.1:5173"
        ));

        // ✅ Dozvoli sve metode
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"
        ));

        // ✅ Dozvoli sve headere
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // ✅ Izloži headere
        configuration.setExposedHeaders(Arrays.asList(
                "Authorization", "Content-Type"
        ));

        // ✅ Dozvoli credentials
        configuration.setAllowCredentials(true);

        // Cache preflight za 1h
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}