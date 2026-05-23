package com.galleriabank.backend.controller;

import com.galleriabank.backend.dto.requests.LoginRequestDTO;
import com.galleriabank.backend.dto.responses.LoginResponseDTO;
import com.galleriabank.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@Tag(name = "Auth", description = "Auth Routes")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticate users")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO body) {
        String token = this.authService.login(body);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
