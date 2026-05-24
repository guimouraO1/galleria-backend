package com.galleriabank.backend.dto.responses;

import java.time.LocalDateTime;

public record ListClientResponseDTO(
        Long id,
        String name,
        String cpf,
        String phone,
        LocalDateTime createdAt
) {
}
