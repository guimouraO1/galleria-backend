package com.galleriabank.backend.controller;

import com.galleriabank.backend.dto.requests.CreateUserRequestDTO;
import com.galleriabank.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User", description = "User Routes")
@RequiredArgsConstructor
public class UserController {

    final private UserService userService;

    @PostMapping()
    @Operation(summary = "Create", description = "Create users")
    public ResponseEntity<Void> create(@RequestBody @Valid CreateUserRequestDTO body) {
        this.userService.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
