package com.galleriabank.backend.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateProductRequestDTO(

        @NotBlank(message = "Description is required")
        @Size(min = 3, max = 254, message = "Description must be between 3 and 254 characters")
        @Schema(example = "Notebook Dell Inspiron 15")
        String description,

        @NotNull(message = "Value is required")
        @DecimalMin(value = "0.01",  message = "Value must be greater than zero")
        @DecimalMax(value = "9999999999999.99", message = "Value exceeds maximum allowed")
        @Schema(example = "2499.90")
        BigDecimal value
) {
}