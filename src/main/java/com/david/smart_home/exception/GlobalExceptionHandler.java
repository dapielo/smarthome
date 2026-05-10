package com.david.smart_home.exception;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import jakarta.servlet.ServletException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionBody> handleUserNotFound(UserNotFoundException e, WebRequest request){
        ExceptionBody body = new ExceptionBody(
        e.getMessage(), 
        HttpStatus.NOT_FOUND.value(), 
        LocalDateTime.now(), 
        request.getDescription(false));
        return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DeviceNotFoundException.class)
    public ResponseEntity<ExceptionBody> handleDeviceNotFound(DeviceNotFoundException e, WebRequest request){
        ExceptionBody body = new ExceptionBody(
            e.getMessage(), 
            HttpStatus.NOT_FOUND.value(), 
            LocalDateTime.now(), 
            request.getDescription(false));
        return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UploadsDirectoryCreationException.class)
    public ResponseEntity<ExceptionBody> handleUploadsDirectoryCreation(UploadsDirectoryCreationException e, WebRequest request){
        ExceptionBody body = new ExceptionBody(
            e.getMessage(), 
            HttpStatus.INTERNAL_SERVER_ERROR.value(), 
            LocalDateTime.now(), 
            request.getDescription(false));
        return new ResponseEntity<>(body,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileCopyException.class)
    public ResponseEntity<ExceptionBody> handleFileCopy(FileCopyException e, WebRequest request){
        ExceptionBody body = new ExceptionBody(
            e.getMessage(), 
            HttpStatus.INTERNAL_SERVER_ERROR.value(), 
            LocalDateTime.now(), 
            request.getDescription(false));
        return new ResponseEntity<>(body,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ExceptionBody> handleFileNotFound(FileNotFoundException e, WebRequest request){
        ExceptionBody body = new ExceptionBody(
            e.getMessage(), 
            HttpStatus.NOT_FOUND.value(),
            LocalDateTime.now(), 
            request.getDescription(false));
        return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ExceptionBody> handleJwtFilter(IOException e, WebRequest request) {
        e.printStackTrace();
        ExceptionBody body = new ExceptionBody(
            "Error en la petición: "+ e.getMessage(), 
            HttpStatus.INTERNAL_SERVER_ERROR.value(), 
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), 
            request.getDescription(false));
        return new ResponseEntity<>(body,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ExceptionBody> handleJwtFilter(ServletException e, WebRequest request) {
        e.printStackTrace();
        ExceptionBody body = new ExceptionBody(
            "Error en la peticion: " + e.getMessage(), 
            HttpStatus.INTERNAL_SERVER_ERROR.value(), 
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), 
            request.getDescription(false));
        return new ResponseEntity<>(body,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IncorrectUserPasswordException.class)
    public ResponseEntity<ExceptionBody> handleIncorrectUserPassword(IncorrectUserPasswordException e, WebRequest request) {
        ExceptionBody body = new ExceptionBody(
            e.getMessage(), 
            HttpStatus.BAD_REQUEST.value(), 
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), 
            request.getDescription(false));
        return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
    }
}
