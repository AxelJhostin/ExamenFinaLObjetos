package ec.webmarket.restful.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // 🔹 Deshabilita CSRF para permitir `POST`
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1.0/usuarios/register").permitAll()
                .requestMatchers("/api/v1.0/dentistas/register").permitAll() // 🔹 Permitir registro de dentistas
                .anyRequest().permitAll()
            );

        return http.build();
    }
}
