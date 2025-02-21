package ec.webmarket.restful.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad para permitir todas las solicitudes sin autenticación.
 * Esto es solo para pruebas y desarrollo.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // 🔹 Deshabilita CSRF para permitir `POST`
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1.0/usuarios/register").permitAll() // 🔹 Permitir el registro sin autenticación
                .anyRequest().permitAll()
            );

        return http.build();
    }
}
