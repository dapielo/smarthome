package com.david.smart_home.exception;

public class DeviceNotFoundException extends RuntimeException{

    public DeviceNotFoundException(String mensaje){
        super(mensaje);
    }
}
