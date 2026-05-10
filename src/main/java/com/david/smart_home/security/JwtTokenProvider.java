package com.david.smart_home.security;

import java.util.Date;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
    private final String JWT_SECRET_KEY = "Fz3myZwYEjyBpa1V+dVYZm6e9+HJ3dSozteqmCJALMBa2tU2I9xb/nS/mX5fnkVY82uMxp8TWeCD63ZNdeuNXg==";
    private final Long JWT_EXPIRATION_HOURS = 86400000l;

    public String generarToken(Authentication auth) {
        String username = auth.getName();
        List<String> roles = auth.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList();
        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_HOURS))
                .signWith(Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes()))
                .compact();
    }

    public boolean validarToken(String token) {
        try{
            Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes()))
            .build()
            // El parseSignedClaims verfica la firma del token, verifica si ha expirado, y luego devuelve un objeto Jws<Claims> que contiene los claims en su payload
            // lo interesante es que si falla alguna de las verificaciones, lanza una excepcion. 
            .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    } 

    public String getUsernameFromToken(String token){
        return  Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token){
        return Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes()))
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .get("roles", List.class);
    }
}
