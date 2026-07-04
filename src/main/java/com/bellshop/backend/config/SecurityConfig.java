package com.bellshop.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Pour hacher proprement les mots de passe admin
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 🟢 CODE AJOUTÉ : Activer la configuration CORS personnalisée définie plus bas
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // On désactive le CSRF car on utilise des tokens JWT (Stateless)
            .csrf(csrf -> csrf.disable())
            
            // Gestion de session Stateless
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))            
            
            // Définition des règles d'accès aux routes
            .authorizeHttpRequests(auth -> auth
                // 🟢 ACCÈS PUBLIC : Vitrine produits
                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                // 🟢 ACCÈS PUBLIC : Passer une commande (Achat direct ou sur-mesure)
                .requestMatchers(HttpMethod.POST, "/api/orders").permitAll()
                // 🟢 ACCÈS PUBLIC : Connexion et Swagger UI
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                
                // 🔴 ACCÈS PROTÉGÉ : Tout le reste demande d'être authentifié (Dashboard Admin)
                .anyRequest().authenticated()
            );

        // On injecte notre filtre JWT avant le filtre de sécurité par défaut de Spring
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 🟢 BEAN AJOUTÉ : Configuration explicite des règles CORS pour ton application
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Autorise uniquement ton frontend Next.js à faire des requêtes
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); 
        
        // Autorise les méthodes HTTP standards que tu vas utiliser sur ton dashboard
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); 
        
        // Autorise l'en-tête 'Authorization' pour que ton token JWT passe sans encombre
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Cache-Control"));
        
        // Permet l'envoi de cookies/crédentials si nécessaire
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Applique ces règles sur TOUTES les routes de l'API
        return source;
    }
}