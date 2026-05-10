package com.david.smart_home.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String mensaje) {
        super(mensaje);
    }
}
