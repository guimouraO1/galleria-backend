package com.galleriabank.backend.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateProductRequestDTO(

        @Size(min = 3, max = 254, message = "Description must be between 3 and 254 characters")
        @Schema(example = "Notebook Dell 14")
        String description,

        @DecimalMin(value = "0.01", message = "Value must be greater than zero")
        @DecimalMax(value = "9999999999999.99", message = "Value exceeds maximum allowed")
        @Digits(integer = 13, fraction = 2, message = "Value must have up to 13 integer digits and 2 decimal places")
        @Schema(example = "2499.90")
        BigDecimal value
) {}