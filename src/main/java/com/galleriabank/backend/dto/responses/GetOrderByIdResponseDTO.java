package com.galleriabank.backend.dto.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record GetOrderByIdResponseDTO(
        Long id,
        String referenceCode,
        String description,
        LocalDateTime issuedAt,
        Long clientId,
        List<GetProductByIdResponseDTO> products,
        BigDecimal total
) {}