package com.galleriabank.backend.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(

        @NotBlank
        @Size(min = 3, max = 254)
        String name,

        @NotBlank
        @Size(min = 3, max = 100)
        String login,

        @NotBlank
        @Size(min = 6, max = 255)
        String password

) {}
