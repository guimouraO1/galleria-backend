package com.galleriabank.backend.controller;

import com.galleriabank.backend.dto.requests.CreateProductRequestDTO;
import com.galleriabank.backend.dto.requests.UpdateProductRequestDTO;
import com.galleriabank.backend.dto.responses.GetProductByIdResponseDTO;
import com.galleriabank.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@Tag(name = "Product", description = "Product Routes")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class ProductController {

    final private ProductService productService;

    @PostMapping()
    @Operation(summary = "Create product", description = "Create product")
    public ResponseEntity<Void> create(@RequestBody @Valid CreateProductRequestDTO body) {
        this.productService.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Get product by ID")
    public ResponseEntity<GetProductByIdResponseDTO> get(@PathVariable Long id) {
        GetProductByIdResponseDTO product = this.productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product by ID", description = "Update product by ID")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid UpdateProductRequestDTO body) {
        this.productService.update(id, body.description(), body.value());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by ID", description = "Delete product by ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
