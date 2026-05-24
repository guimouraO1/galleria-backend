package com.galleriabank.backend.repository;

import com.galleriabank.backend.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByDeletedAtIsNullOrderByCreatedAtDesc(Pageable pageable);

    List<Product> findByDeletedAtIsNullAndCreatedAtBeforeOrderByCreatedAtDesc(LocalDateTime cursor, Pageable pageable);

    Long countByDeletedAtIsNullAndCreatedAtGreaterThanEqualAndCreatedAtBefore(LocalDateTime start, LocalDateTime end);
}
