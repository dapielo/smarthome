package com.david.smart_home.mapper;

import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import com.david.smart_home.dto.DeviceDTO;
import com.david.smart_home.model.Device;

// La anotación component, hace que se instancie este objeto para la inyeccion de dependencias, hace lo mismo que hace @bean
// pero bean es para clases que no son de spring con lo cual no podemos anotar, y component es para clases que creamos nosotros y podemos anotar
@Component
public class DeviceMapper {
    
    public Device toEntity(DeviceDTO dto) {
        return new Device(
            dto.name(), 
            dto.type(), 
            dto.category(), 
            dto.location(), 
            dto.brand(), 
            dto.model());
    }

    public DeviceDTO toDTO(Device device) {
        return new DeviceDTO(
            device.getId(), 
            device.getName(), 
            device.getType(), 
            device.getCategory(), 
            device.getLocation(), 
            device.getBrand(), 
            device.getModel(), 
            device.getStatus().name(), 
            device.getStatusDetail(), 
            device.getCreatedAt().truncatedTo(ChronoUnit.SECONDS).toString(), 
            device.getLastUpdated().truncatedTo(ChronoUnit.SECONDS).toString(), 
            device.getLastModifiedBy(), 
            device.getFoto());
    }

}
