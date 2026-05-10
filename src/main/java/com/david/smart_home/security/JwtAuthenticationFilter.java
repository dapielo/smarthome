package com.david.smart_home.security;

import java.io.IOException;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String token = getTokenFromRequest(request);
        
        if (token != null && tokenProvider.validarToken(token)){
            String user = tokenProvider.getUsernameFromToken(token);
            // Obtenemos el listado de roles a partir del token
            List<String> roles = tokenProvider.getRolesFromToken(token);
            // A partir de los roles crealos el listado de Authorities con un map
            List<SimpleGrantedAuthority> authorities = roles.stream().map(auth -> new SimpleGrantedAuthority(auth)).toList();

            // Creamos el Authentication para el SecurityContextHolder, tenemos que usar el constructor de 3 parmámetros porque segun ví
            // el que tiene dos parámetros, devuelve false siempre en el método isAuthenticated() (corrigeme si no es asi)
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
            // Le pasamos las credenciales de usuario al SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
            // pasamos el filtro al siguente, en este caso el filtro de autenticacion de usuario/contraseña, recibe el objeto
            // request y el response aunque en este caso no los usamos para nada
            filterChain.doFilter(request, response);
    }

    // Obtenemos el token a partir del HttpServletRequest que recibe el doFilterInternal y le pasa a este método,
    //  accediendo a sus caveceras con getHeader
    public String getTokenFromRequest(HttpServletRequest request) {
        
        String tokenBearer = request.getHeader("Authorization");
        if (tokenBearer != null && tokenBearer.startsWith("Bearer ")){
            return tokenBearer.substring(7); // a paritr del caracter 7 empieza el token per se
        }
        return null;
    }
}
