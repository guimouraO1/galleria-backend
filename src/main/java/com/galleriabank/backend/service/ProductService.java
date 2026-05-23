package com.galleriabank.backend.service;

import com.galleriabank.backend.domain.Product;
import com.galleriabank.backend.dto.requests.CreateProductRequestDTO;
import com.galleriabank.backend.dto.responses.GetProductByIdResponseDTO;
import com.galleriabank.backend.exceptions.ProductDeletedException;
import com.galleriabank.backend.exceptions.ProductNotFoundException;
import com.galleriabank.backend.repository.ProductRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void create(@NonNull CreateProductRequestDTO body) {
        Product product = new Product();

        product.setDescription(body.description());
        product.setValue(body.value());

        productRepository.save(product);
    }

    public GetProductByIdResponseDTO findById(Long id) {
        Optional<Product> optionalProduct = this.productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException();
        }

        Product product = optionalProduct.get();
        if (product.getDeletedAt() != null) {
            throw new ProductDeletedException();
        }

        return new GetProductByIdResponseDTO(
                product.getId(),
                product.getDescription(),
                product.getValue()
        );
    }

    public void update(Long id, String description, BigDecimal value) {
        Optional<Product> optionalProduct = this.productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException();
        }

        Product product = optionalProduct.get();
        if (product.getDeletedAt() != null) {
            throw new ProductDeletedException();
        }

        if (description != null && !description.isBlank()) {
            product.setDescription(description);
        }

        if (value != null) {
            product.setValue(value);
        }

        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);
    }

    public void delete(Long id) {
        Optional<Product> optionalProduct = this.productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException();
        }

        Product product = optionalProduct.get();
        if (product.getDeletedAt() != null) {
            throw new ProductDeletedException();
        }

        product.setDeletedAt(LocalDateTime.now());
        productRepository.save(product);
    }
}
