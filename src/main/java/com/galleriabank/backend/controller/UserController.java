package com.galleriabank.backend.controller;

import com.galleriabank.backend.dto.requests.CreateUserRequestDTO;
import com.galleriabank.backend.dto.requests.UpdateUserRequestDTO;
import com.galleriabank.backend.dto.responses.GetUserByIdResponseDTO;
import com.galleriabank.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "User Routes")
@RequiredArgsConstructor
public class UserController {

    final private UserService userService;

    @PostMapping()
    @Operation(summary = "Create user", description = "Create user")
    public ResponseEntity<Void> create(@RequestBody @Valid CreateUserRequestDTO body) {
        this.userService.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Get user by ID")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<GetUserByIdResponseDTO> get(@PathVariable Long id) {
        GetUserByIdResponseDTO user = this.userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Put user by ID", description = "Update user by ID")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid UpdateUserRequestDTO body) {
        this.userService.update(id, body.name(), body.password());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by ID", description = "Delete user by ID")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
