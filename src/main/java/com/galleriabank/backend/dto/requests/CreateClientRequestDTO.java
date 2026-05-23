package com.galleriabank.backend.dto.requests;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateClientRequestDTO(

        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 254, message = "Name must be between 3 and 254 characters")
        @Schema(example = "John Doe")
        String name,

        @NotBlank(message = "CPF is required")
        @Pattern(regexp = "\\d{11}", message = "CPF must contain exactly 11 digits")
        @Schema(example = "46300022233")
        String cpf,

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "\\d{10,11}", message = "Phone must contain 10 or 11 digits")
        @Schema(example = "19989958888")
        String phone
) {}