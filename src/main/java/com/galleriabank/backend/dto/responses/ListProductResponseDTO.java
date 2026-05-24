package com.galleriabank.backend.dto.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ListProductResponseDTO(
        Long id,
        String description,
        BigDecimal value,
        LocalDateTime createdAt
) {
}
