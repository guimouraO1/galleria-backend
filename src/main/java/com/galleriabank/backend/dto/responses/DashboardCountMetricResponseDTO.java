package com.galleriabank.backend.dto.responses;

import java.math.BigDecimal;

public record DashboardCountMetricResponseDTO(
        Long current,
        Long previous,
        BigDecimal percentage
) {}
