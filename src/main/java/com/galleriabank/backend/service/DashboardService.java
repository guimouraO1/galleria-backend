package com.galleriabank.backend.service;

import com.galleriabank.backend.dto.responses.DashboardCountMetricResponseDTO;
import com.galleriabank.backend.dto.responses.DashboardMoneyMetricResponseDTO;
import com.galleriabank.backend.dto.responses.DashboardResponseDTO;
import com.galleriabank.backend.repository.ClientRepository;
import com.galleriabank.backend.repository.OrderRepository;
import com.galleriabank.backend.repository.ProductRepository;
import com.galleriabank.backend.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public DashboardResponseDTO get(@NonNull LocalDate date) {
        LocalDate currentStartDate = date.withDayOfMonth(1);
        LocalDate currentEndDate = date;

        LocalDate previousEndDate = date.minusMonths(1);
        LocalDate previousStartDate = previousEndDate.withDayOfMonth(1);

        LocalDateTime currentStart = currentStartDate.atStartOfDay();
        LocalDateTime currentEnd = currentEndDate.plusDays(1).atStartOfDay();
        LocalDateTime previousStart = previousStartDate.atStartOfDay();
        LocalDateTime previousEnd = previousEndDate.plusDays(1).atStartOfDay();

        BigDecimal currentOrdersTotal = this.orderRepository.sumTotalByIssuedAtBetween(currentStart, currentEnd);
        BigDecimal previousOrdersTotal = this.orderRepository.sumTotalByIssuedAtBetween(previousStart, previousEnd);

        Long currentOrdersCount = this.orderRepository.countByIssuedAtGreaterThanEqualAndIssuedAtBefore(currentStart, currentEnd);
        Long previousOrdersCount = this.orderRepository.countByIssuedAtGreaterThanEqualAndIssuedAtBefore(previousStart, previousEnd);

        Long currentNewProducts = this.productRepository.countByDeletedAtIsNullAndCreatedAtGreaterThanEqualAndCreatedAtBefore(currentStart, currentEnd);
        Long previousNewProducts = this.productRepository.countByDeletedAtIsNullAndCreatedAtGreaterThanEqualAndCreatedAtBefore(previousStart, previousEnd);

        Long currentNewClients = this.clientRepository.countByDeletedAtIsNullAndCreatedAtGreaterThanEqualAndCreatedAtBefore(currentStart, currentEnd);
        Long previousNewClients = this.clientRepository.countByDeletedAtIsNullAndCreatedAtGreaterThanEqualAndCreatedAtBefore(previousStart, previousEnd);

        Long currentNewUsers = this.userRepository.countByDeletedAtIsNullAndCreatedAtGreaterThanEqualAndCreatedAtBefore(currentStart, currentEnd);
        Long previousNewUsers = this.userRepository.countByDeletedAtIsNullAndCreatedAtGreaterThanEqualAndCreatedAtBefore(previousStart, previousEnd);

        return new DashboardResponseDTO(
                date,
                currentStartDate,
                currentEndDate,
                previousStartDate,
                previousEndDate,
                buildMoneyMetric(currentOrdersTotal, previousOrdersTotal),
                buildCountMetric(currentOrdersCount, previousOrdersCount),
                buildCountMetric(currentNewProducts, previousNewProducts),
                buildCountMetric(currentNewClients, previousNewClients),
                buildCountMetric(currentNewUsers, previousNewUsers)
        );
    }

    private DashboardMoneyMetricResponseDTO buildMoneyMetric(BigDecimal current, BigDecimal previous) {
        return new DashboardMoneyMetricResponseDTO(
                current,
                previous,
                calculatePercentage(current, previous)
        );
    }

    private DashboardCountMetricResponseDTO buildCountMetric(Long current, Long previous) {
        return new DashboardCountMetricResponseDTO(
                current,
                previous,
                calculatePercentage(BigDecimal.valueOf(current), BigDecimal.valueOf(previous))
        );
    }

    private BigDecimal calculatePercentage(BigDecimal current, BigDecimal previous) {
        if (previous.compareTo(BigDecimal.ZERO) == 0) {
            if (current.compareTo(BigDecimal.ZERO) == 0) {
                return BigDecimal.ZERO;
            }

            return BigDecimal.valueOf(100);
        }

        return current
                .subtract(previous)
                .multiply(BigDecimal.valueOf(100))
                .divide(previous, 2, RoundingMode.HALF_UP);
    }
}
