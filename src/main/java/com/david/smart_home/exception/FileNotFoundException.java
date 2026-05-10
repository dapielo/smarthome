package com.david.smart_home.exception;

public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(String mensaje){
        super(mensaje);
    }
}
