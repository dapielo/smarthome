package com.david.smart_home.configuration;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.david.smart_home.model.User;
import com.david.smart_home.repository.UserRepository;
import lombok.RequiredArgsConstructor;

// Con esta clase instanciamos a los usuarios
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Solo si es la primera vez que lo arrancamos
        if (userRepository.count() == 0) {
            // Guardamos las contraseñas encriptadas
            User admin = new User("admin",passwordEncoder.encode("1234"));
            // Los usuarios no tiene asignados roles, mira a ver en la respuesta de claude como gestionar esto
            User user = new User("user", passwordEncoder.encode("0000"));        
            userRepository.saveAll(List.of(admin,user));
        }
    }
}
