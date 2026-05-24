package com.galleriabank.backend.controller;

import com.galleriabank.backend.dto.requests.CreateClientRequestDTO;
import com.galleriabank.backend.dto.requests.UpdateClientRequestDTO;
import com.galleriabank.backend.dto.responses.CursorPaginatedResponseDTO;
import com.galleriabank.backend.dto.responses.GetClientByIdResponseDTO;
import com.galleriabank.backend.dto.responses.ListClientResponseDTO;
import com.galleriabank.backend.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/client")
@Tag(name = "Client", description = "Client Routes")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class ClientController {

    final private ClientService clientService;

    @PostMapping()
    @Operation(summary = "Create client", description = "Create client")
    public ResponseEntity<Void> create(@RequestBody @Valid CreateClientRequestDTO body) {
        this.clientService.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get client by ID", description = "Get client by ID")
    public ResponseEntity<GetClientByIdResponseDTO> get(@PathVariable Long id) {
        GetClientByIdResponseDTO client = this.clientService.findById(id);
        return ResponseEntity.ok(client);
    }

    @GetMapping()
    @Operation(summary = "List clients", description = "List clients with date cursor pagination")
    public ResponseEntity<CursorPaginatedResponseDTO<ListClientResponseDTO>> list(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime cursor,
            @RequestParam(required = false) Integer limit
    ) {
        CursorPaginatedResponseDTO<ListClientResponseDTO> clients = this.clientService.list(cursor, limit);
        return ResponseEntity.ok(clients);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Put client by ID", description = "Update client by ID")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid UpdateClientRequestDTO body) {
        this.clientService.update(id, body.name(), body.phone());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete client by ID", description = "Delete client by ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
