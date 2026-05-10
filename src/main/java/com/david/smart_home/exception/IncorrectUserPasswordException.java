package com.david.smart_home.exception;

public class IncorrectUserPasswordException extends RuntimeException{

    public IncorrectUserPasswordException(String mensaje){
        super(mensaje);
    }
}
