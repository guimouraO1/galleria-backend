package com.galleriabank.backend.dto.responses;

import java.math.BigDecimal;

public record DashboardMoneyMetricResponseDTO(
        BigDecimal current,
        BigDecimal previous,
        BigDecimal percentage
) {}
