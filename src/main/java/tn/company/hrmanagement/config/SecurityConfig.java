package tn.company.hrmanagement.config;

import tn.company.hrmanagement.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Public : login, Swagger, documentation
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // Lecture (GET) : accessible a tout utilisateur authentifie, quel que soit son role
                        .requestMatchers("GET", "/api/**").authenticated()

                        // Ecriture sur departements/employes : reserve a ADMIN et MANAGER (RH)
                        .requestMatchers("/api/departments/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/api/employees/**").hasAnyRole("ADMIN", "MANAGER")

                        // Validation/refus des conges et autorisations : ADMIN et MANAGER uniquement
                        .requestMatchers("/api/leave-requests/*/approve").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/api/leave-requests/*/reject").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/api/absence-requests/*/approve").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/api/absence-requests/*/reject").hasAnyRole("ADMIN", "MANAGER")

                        // Creation de demandes de conge/autorisation : tout utilisateur authentifie
                        .requestMatchers("POST", "/api/leave-requests").authenticated()
                        .requestMatchers("POST", "/api/absence-requests").authenticated()

                        // Dashboard : ADMIN et MANAGER uniquement
                        .requestMatchers("/api/dashboard/**").hasAnyRole("ADMIN", "MANAGER")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}