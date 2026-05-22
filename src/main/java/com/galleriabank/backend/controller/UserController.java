package com.galleriabank.backend.controller;

import com.galleriabank.backend.dto.requests.RegisterRequestDTO;
import com.galleriabank.backend.service.AuthService;
import com.galleriabank.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    final private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register", description = "Register users")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequestDTO body) {
        this.userService.register(body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
