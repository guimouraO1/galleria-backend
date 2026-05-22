package com.galleriabank.backend.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(

        @NotBlank
        String login,

        @NotBlank
        String password

) {}