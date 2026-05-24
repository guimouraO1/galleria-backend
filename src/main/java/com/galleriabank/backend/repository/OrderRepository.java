package com.galleriabank.backend.repository;

import com.galleriabank.backend.domain.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByOrderByIssuedAtDesc(Pageable pageable);

    List<Order> findByIssuedAtBeforeOrderByIssuedAtDesc(LocalDateTime cursor, Pageable pageable);

    Long countByIssuedAtGreaterThanEqualAndIssuedAtBefore(LocalDateTime start, LocalDateTime end);

    @Query(value = """
            SELECT COALESCE(SUM(products.value), 0)
            FROM orders
            JOIN order_products ON order_products.order_id = orders.id
            JOIN products ON products.id = order_products.product_id
            WHERE orders.issued_at >= :start
            AND orders.issued_at < :end
            """, nativeQuery = true)
    BigDecimal sumTotalByIssuedAtBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
