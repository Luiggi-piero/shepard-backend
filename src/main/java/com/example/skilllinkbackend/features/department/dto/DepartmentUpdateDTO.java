package com.example.skilllinkbackend.features.department.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Datos necesarios para editar un departamento")
public record DepartmentUpdateDTO(
        @Schema(description = "ID del departamento", example = "1")
        @NotNull(message = "El id del departamento no puede ser nulo")
        Long id,

        @Schema(description = "Piso del departamento", example = "1")
        @NotNull(message = "El piso no puede ser nulo")
        @Min(value = 1, message = "El piso debe ser al menos 1")
        Integer floor,

        @Schema(description = "Precio por noche", example = "50")
        @NotNull(message = "El precio no puede ser nulo")
        @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
        BigDecimal price,

        @Schema(description = "Código del departamento", example = "D101")
        @NotBlank(message = "El código no puede estar vacío")
        String code,

        @Schema(description = "Cantidad de habitaciones", example = "1")
        @NotNull(message = "La cantidad de habitaciones no puede ser nulo")
        @Min(value = 1, message = "La cantidad debe ser al menos 1")
        Integer roomsCount
) {
}
