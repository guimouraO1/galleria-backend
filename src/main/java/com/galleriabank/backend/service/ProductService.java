package com.galleriabank.backend.service;

import com.galleriabank.backend.domain.Product;
import com.galleriabank.backend.dto.requests.CreateProductRequestDTO;
import com.galleriabank.backend.dto.responses.CursorPaginatedResponseDTO;
import com.galleriabank.backend.dto.responses.GetProductByIdResponseDTO;
import com.galleriabank.backend.dto.responses.ListProductResponseDTO;
import com.galleriabank.backend.exceptions.ProductDeletedException;
import com.galleriabank.backend.exceptions.ProductNotFoundException;
import com.galleriabank.backend.repository.ProductRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final int DEFAULT_LIMIT = 20;
    private static final int MAX_LIMIT = 100;

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

    public CursorPaginatedResponseDTO<ListProductResponseDTO> list(LocalDateTime cursor, Integer limit) {
        int normalizedLimit = normalizeLimit(limit);
        PageRequest pageRequest = PageRequest.of(0, normalizedLimit + 1);

        List<Product> products = cursor == null
                ? this.productRepository.findByDeletedAtIsNullOrderByCreatedAtDesc(pageRequest)
                : this.productRepository.findByDeletedAtIsNullAndCreatedAtBeforeOrderByCreatedAtDesc(cursor, pageRequest);

        boolean hasNext = products.size() > normalizedLimit;
        List<ListProductResponseDTO> items = products.stream()
                .limit(normalizedLimit)
                .map(product -> new ListProductResponseDTO(
                        product.getId(),
                        product.getDescription(),
                        product.getValue(),
                        product.getCreatedAt()
                ))
                .toList();

        LocalDateTime nextCursor = hasNext ? items.getLast().createdAt() : null;

        return new CursorPaginatedResponseDTO<>(items, nextCursor, hasNext);
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

    private int normalizeLimit(Integer limit) {
        if (limit == null || limit < 1) {
            return DEFAULT_LIMIT;
        }

        return Math.min(limit, MAX_LIMIT);
    }
}
