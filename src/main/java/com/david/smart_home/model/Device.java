package com.david.smart_home.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.security.core.context.SecurityContextHolder;

import com.david.smart_home.enums.Status;
import com.david.smart_home.util.Util;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "devices")
@Getter
@Setter
@NoArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    String name;

    String type;
    String category;
    String location;
    String brand;
    String model;
    Status status;
    String statusDetail;
    LocalDateTime createdAt;
    LocalDateTime lastUpdated;
    String lastModifiedBy;
    String foto;

    // Este constructor recibe los parámetros que no sean el id ni los de fechas poruqe son los que generamos nosotros de forma interna
    public Device(@NotNull String name, String type, String category, String location, String brand, String model) {
        this.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.lastUpdated = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.statusDetail = Util.generarStatus();
        this.status = Status.OFF;
        this.lastModifiedBy = SecurityContextHolder.getContext().getAuthentication().getName();
        this.name = name;
        this.type = type;
        this.category = category;
        this.location = location;
        this.brand = brand;
        this.model = model;
    }

}
