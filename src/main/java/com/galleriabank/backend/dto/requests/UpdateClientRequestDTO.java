package com.galleriabank.backend.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateClientRequestDTO(
        @Size(min = 3, max = 254, message = "Name must be between 3 and 254 characters")
        @Schema(example = "John Doe")
        String name,

        @Pattern(regexp = "\\d{10,11}", message = "Phone must contain 10 or 11 digits")
        @Schema(example = "19989958888")
        String phone
) {}
