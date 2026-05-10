package com.david.smart_home.controller;

import org.springframework.web.bind.annotation.RestController;
import com.david.smart_home.exception.FileNotFoundException;
import com.david.smart_home.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/devices/files")
public class FileController {

    @Value("${upload.dir}")
    private String uploads;

    private final FileService fileService;

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> servirArchivo(@PathVariable String filename, HttpServletRequest request) {
        Resource file = fileService.servirArchivo(filename);
        try{
            String contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
            if (contentType == null) {
                // el MIME nos puede dar null, en ese caso ponemos octet-stream, que es un archivo de aplicacion para datos binarios
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok()
            .header("Content-Type", contentType)
            .body(file);
        } catch (IOException e) {
            throw new FileNotFoundException("No se ha podido encontar el archivo");
        }
    }
}
