package com.galleriabank.backend.controller;

import com.galleriabank.backend.dto.requests.CreateOrderRequestDTO;
import com.galleriabank.backend.dto.responses.GetOrderByIdResponseDTO;
import com.galleriabank.backend.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Tag(name = "Order", description = "Order Routes")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    @Operation(summary = "Create order", description = "Create order")
    public ResponseEntity<Void> create(@RequestBody @Valid CreateOrderRequestDTO body) {
        this.orderService.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Get order by ID")
    public ResponseEntity<GetOrderByIdResponseDTO> get(@PathVariable Long id) {
        GetOrderByIdResponseDTO product = this.orderService.findById(id);
        return ResponseEntity.ok(product);
    }
}
