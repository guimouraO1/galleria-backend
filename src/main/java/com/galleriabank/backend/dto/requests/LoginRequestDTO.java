package com.galleriabank.backend.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(

        @NotBlank(message = "Login is required")
        @Schema(example = "nickname")
        String login,

        @NotBlank(message = "Password is required")
        @Schema(example = "myP@@W0rD")
        String password
) {}