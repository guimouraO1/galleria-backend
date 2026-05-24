package com.galleriabank.backend.dto.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ListOrderResponseDTO(
        Long id,
        String referenceCode,
        String description,
        LocalDateTime issuedAt,
        Long clientId,
        BigDecimal total
) {
}
