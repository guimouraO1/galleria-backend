package com.galleriabank.backend.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequestDTO(

        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 254, message = "Name must be between 3 and 254 characters")
        @Schema(example = "Rocha")
        String name,

        @NotBlank(message = "Login is required")
        @Size(min = 3, max = 100, message = "Login must be between 3 and 100 characters")
        @Schema(example = "nickname")
        String login,

        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters")
        @Schema(example = "myP@@ssW0rD")
        String password
) {}