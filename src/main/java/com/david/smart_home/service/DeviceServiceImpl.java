package com.david.smart_home.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.david.smart_home.dto.DeviceDTO;
import com.david.smart_home.enums.Status;
import com.david.smart_home.exception.DeviceNotFoundException;
import com.david.smart_home.mapper.DeviceMapper;
import com.david.smart_home.model.Device;
import com.david.smart_home.repository.DeviceRepository;
import com.david.smart_home.util.Util;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService{
    private final FileService fileService;
    private final DeviceRepository deviceRepository;
    private final DeviceMapper mapper;

    public List<DeviceDTO> obtenerTodos() {
        return deviceRepository.findAll().stream().map(mapper::toDTO).toList(); 
    }

    public DeviceDTO obtenerPorId(Long id) {
        return deviceRepository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new DeviceNotFoundException("No existe el dispositivo con id "+id));
    }

    public DeviceDTO guardar(DeviceDTO dto, MultipartFile imagen){
        // Pasamos el DTO a Entity
        Device nuevo = mapper.toEntity(dto);
        // Ahora almacenamos la imagen en el servidor y guardamos su nombre en una variable
        String fotoNombre = fileService.store(imagen);
        // Establecemos el nombre del recurso almacenado en el servidor dentro de la entity, para que el cliente sepa el nombre con el que se lo tiene que
        // pedir al endpoint que le ofrecemos para ello "/api/devices/files" si no ha enviado foto tengo diseñado el controlador para que almacene una cadena vacía
        // que se sobreentiende en el contrato con el front que representa la ausencia de foto (no enviar foto al guardar no hace fallar la peticion)
        nuevo.setFoto(fotoNombre);
        return mapper.toDTO(deviceRepository.save(nuevo));
    }

    public DeviceDTO actualizar(Long id, DeviceDTO dto, MultipartFile imagen){
        Device existente = deviceRepository.findById(id)
                            .orElseThrow(() -> new DeviceNotFoundException("No existe el dispositivo con id " + id));

        String fotoNueva = fileService.store(imagen);
        existente.setBrand(dto.brand());
        existente.setCategory(dto.category());
        existente.setFoto(fotoNueva.isBlank() ? existente.getFoto() : fotoNueva);
        // Usamos el SEcurityContextHolder para saber quien ha hecho esta petición (si no tuviese permiso la peticion ni siquiera
        // llegaría aquí)
        existente.setLastModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        existente.setLastUpdated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        existente.setModel(dto.model());
        existente.setName(dto.name());
        existente.setStatus(Status.valueOf(dto.status().toUpperCase()));
        existente.setStatusDetail(Util.generarStatus());
        existente.setType(dto.type());
        existente.setLocation(dto.location());

        // Guardamos y devolvemos convertido a DTO una vez modificado
        return mapper.toDTO(deviceRepository.save(existente));
    }

    public DeviceDTO actualizarStatus(Long id){
        Device existente = deviceRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException("No existe el dispositivo con id "+id));
        // Cambiamos la hora de actualizacion
        existente.setLastUpdated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        // Cambiamos su status en función del que tenía
        Status newStatus = existente.getStatus() == Status.OFF ? Status.ON : Status.OFF;
        existente.setStatus(newStatus);
        // Guardamos y devolvemos el actualizado
        return mapper.toDTO(deviceRepository.save(existente));
    }

    public void borrar(Long id) {
        Device existente = deviceRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException("No existen un dispositivo con id "+id));
        deviceRepository.delete(existente);
    }
}
