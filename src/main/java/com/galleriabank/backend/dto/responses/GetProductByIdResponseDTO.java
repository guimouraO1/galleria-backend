package com.galleriabank.backend.dto.responses;

import java.math.BigDecimal;

public record GetProductByIdResponseDTO(
        Long id,
        String description,
        BigDecimal value
) {
}