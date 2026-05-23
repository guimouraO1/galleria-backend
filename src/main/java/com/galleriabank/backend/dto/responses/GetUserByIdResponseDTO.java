package com.galleriabank.backend.dto.responses;

public record GetUserByIdResponseDTO(
        Long id,
        String name,
        String login
) {
}