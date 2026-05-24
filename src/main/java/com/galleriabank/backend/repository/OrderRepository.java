package com.galleriabank.backend.repository;

import com.galleriabank.backend.domain.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByOrderByIssuedAtDesc(Pageable pageable);

    List<Order> findByIssuedAtBeforeOrderByIssuedAtDesc(LocalDateTime cursor, Pageable pageable);
}
