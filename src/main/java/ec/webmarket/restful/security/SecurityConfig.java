package ec.webmarket.restful.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1.0/usuarios/register", "/api/v1.0/usuarios/login").permitAll() // 🔹 Permite acceso sin autenticación
                .anyRequest().authenticated() // 🔹 Protege todos los demás endpoints
            )
            .httpBasic(); // 🔹 Habilita autenticación básica

        return http.build();
    }
}
