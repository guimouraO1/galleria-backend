package com.galleriabank.backend.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserRequestDTO(
        @Size(min = 3, max = 254, message = "Name must be between 3 and 254 characters")
        @Schema(example = "Rocha da Silva")
        String name,

        @Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters")
        @Schema(example = "myPass@02")
        @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least 1 lowercase letter")
        @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least 1 uppercase letter")
        @Pattern(regexp = ".*\\d.*", message = "Password must contain at least 1 number")
        @Pattern(regexp = ".*[^A-Za-z\\d].*", message = "Password must contain at least 1 special character")
        String password)
{
    @AssertTrue(message = "At least name or password must be provided")
    public boolean isValidUpdate() {
        return name != null || password != null;
    }
}
