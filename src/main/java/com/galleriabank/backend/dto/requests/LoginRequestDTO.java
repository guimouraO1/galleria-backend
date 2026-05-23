package com.galleriabank.backend.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(

        @NotBlank(message = "Login is required")
        @Size(min = 3, max = 100, message = "Login must be between 3 and 100 characters")
        @Schema(example = "nickname")
        String login,

        @NotBlank(message = "Password is required")
        @Schema(example = "myPass@01")
        @Size(min = 1, message = "Password must be at least 1 character")
        String password
) {}