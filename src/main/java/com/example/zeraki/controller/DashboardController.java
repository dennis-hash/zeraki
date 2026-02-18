package com.example.zeraki.controller;

import com.example.zeraki.dto.ApiResponse;
import com.example.zeraki.dto.DashBoardSummary;
import com.example.zeraki.dto.MonthlyRevenueResponse;
import com.example.zeraki.dto.TopCustomerResponse;
import com.example.zeraki.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<DashBoardSummary>> getSummary(
            @RequestParam @DateTimeFormat LocalDate startDate,
            @RequestParam @DateTimeFormat LocalDate endDate) {

        var data = dashboardService.getSummary(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
        return ResponseEntity.ok(new ApiResponse<>(200, "Summary retrieved", data));
    }

    @GetMapping("/top-customers")
    public ResponseEntity<ApiResponse<List<TopCustomerResponse>>> getTopCustomers(
            @RequestParam @DateTimeFormat LocalDate startDate,
            @RequestParam @DateTimeFormat LocalDate endDate) {

        var data = dashboardService.getTopCustomers(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
        return ResponseEntity.ok(new ApiResponse<>(200, "Top customers retrieved", data));
    }

    @GetMapping("/monthly-revenue")
    public ResponseEntity<ApiResponse<List<MonthlyRevenueResponse>>> getMonthlyRevenue(
            @RequestParam @DateTimeFormat LocalDate startDate,
            @RequestParam @DateTimeFormat LocalDate endDate) {

        var data = dashboardService.getMonthlyRevenue(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
        return ResponseEntity.ok(new ApiResponse<>(200, "Monthly revenue retrieved", data));
    }
}