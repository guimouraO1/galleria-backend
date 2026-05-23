package com.galleriabank.backend.dto.responses;

public record GetClientByIdResponseDTO(
        Long id,
        String name,
        String cpf,
        String phone
) {
}
