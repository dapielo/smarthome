package com.david.smart_home.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.david.smart_home.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@Configuration // Esto es para que escanee los beans en esta clase
@EnableWebSecurity // Esto es para habilitar la configruacion de seguridad a nivel de peticiones HTTP
@EnableMethodSecurity // Esto es para habilitar la configuracion de seguridad a nivel de método
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/v3/api/docs/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll()
                .requestMatchers("/swagger/resources/**").permitAll()
                .requestMatchers("/webjars/**").permitAll()
                .requestMatchers("/api/devices/files/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/devices/**").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                .requestMatchers(HttpMethod.PATCH,"/api/devices/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .requestMatchers(HttpMethod.POST,"/api/devices/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/api/devices/**").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers("/auth/login/**").permitAll()
                .anyRequest().permitAll()
            // Tendria que añadir el anyRequest()???
            );
        // Aqui añadimos el fitro que hemos añadido con JWT antes del filtro normal de Usuario y contraseña de spring security
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        // Esto permite que la web pueda ser metida en un iframe siempre que sea del mismo origen (para la consola de h2)
        httpSecurity.headers(h -> h.frameOptions(f -> f.sameOrigin()));
        return httpSecurity.build();
    }

    // Esto lo usamos par aalmacenar las contraseñas encriptadas de los usuarios en la base de datos
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
