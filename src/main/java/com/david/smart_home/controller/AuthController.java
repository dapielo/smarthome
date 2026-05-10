package com.david.smart_home.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.david.smart_home.dto.JwtResponse;
import com.david.smart_home.dto.UserDTO;
import com.david.smart_home.exception.IncorrectUserPasswordException;
import com.david.smart_home.security.JwtTokenProvider;
import com.david.smart_home.service.UserService;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider tokenProvider;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/api/auth/login")
    public ResponseEntity<JwtResponse> login(@RequestBody UserDTO user){
        // Empezamos por comprobar que el usuario esta en la BD y que sus creendicales son correctas
        UserDTO usuario = userService.getUser(user.username());

        // Si la contraseña no es correcta, lanzamos exception, y no se genera el token
        if (!passwordEncoder.matches(user.password(), usuario.password())){
            throw new IncorrectUserPasswordException("La contraseña introducida no es correcta");
        }
        
        // Determinamos el rol a partir del nombre de usuario
        String rol = "ROLE_" + user.username().toUpperCase();
        // Creamos el objeto UsernamePasswordAuthenticationToken, con el nombre, null en las credenciales, y una lista de SimpleGrantedAuthorities con los roles
        Authentication auth = new UsernamePasswordAuthenticationToken(user.username(), null, List.of(new SimpleGrantedAuthority(rol)));
        // Generamos el token a partir de objeto Authentication que lleva el nombre de usuario y los roles
        String token = tokenProvider.generarToken(auth);
        // Creamos el body con el token
        JwtResponse body = new JwtResponse(token);
        // Lo devolvemos al cliente
        return new ResponseEntity<>(body,HttpStatus.ACCEPTED);
    }

}
