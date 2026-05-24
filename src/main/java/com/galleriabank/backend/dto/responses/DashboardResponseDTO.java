package com.galleriabank.backend.dto.responses;

import java.time.LocalDate;

public record DashboardResponseDTO(
        LocalDate date,
        LocalDate currentStartDate,
        LocalDate currentEndDate,
        LocalDate previousStartDate,
        LocalDate previousEndDate,
        DashboardMoneyMetricResponseDTO ordersTotal,
        DashboardCountMetricResponseDTO ordersCount,
        DashboardCountMetricResponseDTO newProducts,
        DashboardCountMetricResponseDTO newClients,
        DashboardCountMetricResponseDTO newUsers
) {}
