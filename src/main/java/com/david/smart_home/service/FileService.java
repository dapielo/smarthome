package com.david.smart_home.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.david.smart_home.exception.FileCopyException;
import com.david.smart_home.exception.FileNotFoundException;
import com.david.smart_home.exception.UploadsDirectoryCreationException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService {

    private Path uploads;

    public FileService(@Value("${upload.dir}") String rutaAlmacenaje){
        uploads = Path.of(rutaAlmacenaje);
        
//        if (!Files.exists(uploads)){
            try{
                Files.createDirectories(uploads);
            } catch (IOException e) {
                System.out.println("Error real: " + e.getMessage());
                throw new UploadsDirectoryCreationException("No se ha podido crear la carpeta de ficheros");
            }
//        }
    }

    public String store(MultipartFile file){
        // Si el archivo no esta presente o está vacío, lo indicamos por consola y devolvemos cadena vacía
        if (file == null || file.isEmpty()){
            log.warn("El archivo estaba vacío, no se ha guardado nada");
            return "";
        } else {
            // Añadimos un nombre unico con la extension original del archivo
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String name = UUID.randomUUID().toString() + extension;
// ME FALTA EL RESOVE
            try{
                // Cuidado, es importante el .resolve() sino te reemplza la carpeta uploads con este archivo
                Files.copy(file.getInputStream(), uploads.resolve(name), StandardCopyOption.REPLACE_EXISTING);
                return name;
            } catch (IOException e){
                throw new FileCopyException("No se ha podido guardar la imagen");
            }
        }
    }

    public void delete(String name) {
        Path ruta = uploads.resolve(name);
        try{
            Files.delete(ruta);
        } catch (IOException e) {
            log.warn("No se ha podido eliminar el archivo " + name);
        }
    }

    // Asi como usamos MultipartFile para recibir las imagenes a traves de una peticion HTTP, cuando l oque queremos es servir una imagen
    // lo que usamos es la clase Resource, que envolvemos en un ResponseEntity para enviar de vuelta al client
    public Resource servirArchivo(String name){
        Path ruta = uploads.resolve(name);
        try {
            Resource archivo = new UrlResource(ruta.toUri());
            return archivo;
        } catch (MalformedURLException e){
            throw new FileNotFoundException("No existe el archivo" + name);
        }
    }

}
