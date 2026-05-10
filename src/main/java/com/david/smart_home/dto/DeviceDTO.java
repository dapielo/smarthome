package com.david.smart_home.dto;

import jakarta.validation.constraints.NotBlank;

// Aplanamos todos los atributos a texto plano
public record DeviceDTO(
    Long id,

    @NotBlank
    String name,

    String type,
    String category,
    String location,
    String brand,
    String model,
    String status,
    String statusDetail,
    String createdAt,
    String lastUpdated,
    String lastModifiedBy,
    String foto
) {}
