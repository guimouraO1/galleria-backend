package com.galleriabank.backend.controller;

import com.galleriabank.backend.dto.responses.DashboardResponseDTO;
import com.galleriabank.backend.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "Dashboard", description = "Dashboard Routes")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class DashboardController {

    final private DashboardService dashboardService;

    @GetMapping()
    @Operation(summary = "Get dashboard statistics", description = "Get dashboard statistics by date")
    public ResponseEntity<DashboardResponseDTO> get(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        DashboardResponseDTO dashboard = this.dashboardService.get(date);
        return ResponseEntity.ok(dashboard);
    }
}
