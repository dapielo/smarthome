package com.david.smart_home.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.david.smart_home.dto.DeviceDTO;

public interface DeviceService {
    List<DeviceDTO> obtenerTodos();
    DeviceDTO obtenerPorId(Long id);
    DeviceDTO guardar(DeviceDTO dto, MultipartFile file);
    DeviceDTO actualizar(Long id, DeviceDTO dto, MultipartFile file);
    DeviceDTO actualizarStatus(Long id);
    void borrar(Long id);
}
