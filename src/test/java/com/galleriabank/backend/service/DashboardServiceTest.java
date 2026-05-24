package com.galleriabank.backend.service;

import com.galleriabank.backend.dto.responses.DashboardResponseDTO;
import com.galleriabank.backend.repository.ClientRepository;
import com.galleriabank.backend.repository.OrderRepository;
import com.galleriabank.backend.repository.ProductRepository;
import com.galleriabank.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DashboardService dashboardService;

    @Test
    void shouldGetDashboard() {
        LocalDateTime currentStart = LocalDateTime.of(2026, 2, 1, 0, 0);
        LocalDateTime currentEnd = LocalDateTime.of(2026, 2, 10, 0, 0);
        LocalDateTime previousStart = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime previousEnd = LocalDateTime.of(2026, 1, 10, 0, 0);

        when(orderRepository.sumTotalByIssuedAtBetween(currentStart, currentEnd)).thenReturn(BigDecimal.valueOf(200));
        when(orderRepository.sumTotalByIssuedAtBetween(previousStart, previousEnd)).thenReturn(BigDecimal.valueOf(100));
        when(orderRepository.countByIssuedAtGreaterThanEqualAndIssuedAtBefore(currentStart, currentEnd)).thenReturn(4L);
        when(orderRepository.countByIssuedAtGreaterThanEqualAndIssuedAtBefore(previousStart, previousEnd)).thenReturn(2L);
        when(productRepository.countByDeletedAtIsNullAndCreatedAtGreaterThanEqualAndCreatedAtBefore(currentStart, currentEnd)).thenReturn(3L);
        when(productRepository.countByDeletedAtIsNullAndCreatedAtGreaterThanEqualAndCreatedAtBefore(previousStart, previousEnd)).thenReturn(1L);
        when(clientRepository.countByDeletedAtIsNullAndCreatedAtGreaterThanEqualAndCreatedAtBefore(currentStart, currentEnd)).thenReturn(5L);
        when(clientRepository.countByDeletedAtIsNullAndCreatedAtGreaterThanEqualAndCreatedAtBefore(previousStart, previousEnd)).thenReturn(4L);
        when(userRepository.countByDeletedAtIsNullAndCreatedAtGreaterThanEqualAndCreatedAtBefore(currentStart, currentEnd)).thenReturn(1L);
        when(userRepository.countByDeletedAtIsNullAndCreatedAtGreaterThanEqualAndCreatedAtBefore(previousStart, previousEnd)).thenReturn(1L);

        DashboardResponseDTO dashboard = dashboardService.get(LocalDate.of(2026, 2, 9));

        assertEquals(LocalDate.of(2026, 2, 1), dashboard.currentStartDate());
        assertEquals(LocalDate.of(2026, 2, 9), dashboard.currentEndDate());
        assertEquals(BigDecimal.valueOf(200), dashboard.ordersTotal().current());
        assertEquals(BigDecimal.valueOf(100).setScale(2), dashboard.ordersTotal().percentage());
        assertEquals(4L, dashboard.ordersCount().current());
        assertEquals(BigDecimal.valueOf(100).setScale(2), dashboard.ordersCount().percentage());
    }
}
