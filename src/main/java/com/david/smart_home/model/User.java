package com.david.smart_home.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users") // user es una palabra reservada en H2
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @NotNull(message = "El usuario no puede tener nombre vacio")
    String username;

    @NotNull(message = "El usuario tiene que tener contraseña")
    String password;

}
