package com.beyefendisinemaci.beyefendisinemaci.config;

import com.beyefendisinemaci.beyefendisinemaci.config.ratelimit.AuthenticatedRateLimit;
import com.beyefendisinemaci.beyefendisinemaci.config.ratelimit.S3RateLimitFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final AuthenticatedRateLimit authenticatedRateLimit;
    private final S3RateLimitFilter s3RateLimitFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // defined as @Bean so it can be injected into AuthService
    // it needs UserDetailsServiceImpl and PasswordEncoder to work
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Public
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/movies/recent").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/movies/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/movies/*/comments").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/movies/*/comments").authenticated()
                        // Admin
                        .requestMatchers("/api/s3/upload/profile-photo").authenticated()
                        .requestMatchers("/api/s3/upload/cover-photo").authenticated()
                        .requestMatchers("/api/s3/upload/video-long").hasAuthority("ADMIN")
                        .requestMatchers("/api/s3/upload/video-short").hasAuthority("ADMIN").requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/tmdb/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/movies/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/movies/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/movies/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(s3RateLimitFilter, JwtFilter.class)
                .addFilterAfter(authenticatedRateLimit, JwtFilter.class);

        return http.build();
    }
}
