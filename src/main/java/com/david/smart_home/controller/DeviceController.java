package com.david.smart_home.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.david.smart_home.dto.DeviceDTO;
import com.david.smart_home.service.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/devices")
public class DeviceController {
    
    private final DeviceService deviceService;
    
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<DeviceDTO>> obtenerTodos() {
        List<DeviceDTO> listado = deviceService.obtenerTodos();
        return ResponseEntity.ok(listado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    // La clase MediaType, tiene los diferrentes mediatype, y sus correspondientes value, los value son la cadena de texto, no objetos MediaType
    // y aqui quiero el texto, equivale a "multipart/form-data", pero escrito a traves de esta clase te evitas errores de sintaxis

    // NOTA: He cambiado el RequestPart de data a @ModelAttribute, porque de esta forma el frontend puede caputaar los campos en un formData
    // y enviar ese formData directamente sin que el servidor se queje, usando requestpart hay que hacer malabares en el cliente para poder enviar

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE) 
    public ResponseEntity<DeviceDTO> crear(@Valid @ModelAttribute DeviceDTO dto, @RequestPart(name = "image", required = false) MultipartFile image) {
        // Si viene sin imagen el FileService esta diseñado para almacenar una cadena vacía
        DeviceDTO nuevo = deviceService.guardar(dto,image);
        return new ResponseEntity<>(nuevo,HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/{id}")
    public ResponseEntity<DeviceDTO> actualizar(@PathVariable Long id, @Valid @ModelAttribute DeviceDTO dto, @RequestPart("image") MultipartFile image) {
        DeviceDTO actualizado = deviceService.actualizar(id, dto, image);
        return new ResponseEntity<>(actualizado,HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("/{id}")
    public ResponseEntity<DeviceDTO> actualizarStatus(@PathVariable Long id){
        DeviceDTO actualizado = deviceService.actualizarStatus(id);
        return new ResponseEntity<>(actualizado,HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        deviceService.borrar(id);
        return ResponseEntity.ok().build();
    }

}
