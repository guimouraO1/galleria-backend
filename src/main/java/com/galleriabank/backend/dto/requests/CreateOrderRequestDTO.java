package com.galleriabank.backend.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateOrderRequestDTO(

        @Schema(example = "Pedido de escritório")
        @Size(max = 254, message = "Description must have at most 254 characters")
        String description,

        @NotNull(message = "Client id is required")
        @Schema(example = "1")
        Long clientId,

        @NotEmpty(message = "At least one product must be provided")
        @Size(max = 10, message = "An order can contain at most 10 products")
        @Schema(example = "[1, 2, 3]")
        List<Long> productIds,

        @Size(min = 3, max = 100, message = "Reference code must be between 3 and 100 characters")
        @Schema(example = "ORD-2026-0001")
        String referenceCode
) {}