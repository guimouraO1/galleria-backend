package com.galleriabank.backend.controller;

import com.galleriabank.backend.dto.requests.CreateClientRequestDTO;
import com.galleriabank.backend.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/client")
@Tag(name = "Client", description = "Client Routes")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class ClientController {

    final private ClientService clientService;
        @PostMapping()
        @Operation(summary = "Create", description = "Create clients")
        public ResponseEntity<Void> create(@RequestBody @Valid CreateClientRequestDTO body) {
            this.clientService.create(body);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
}
