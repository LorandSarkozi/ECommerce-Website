package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnalyticsResponseDto {

    private Long placed;

    private Long shipped;

    private Long delivered;

    private Long currentMontOrders;

    private Long previousMontOrders;

    private Double currentMonthEarnings;

    private Double previousMonthEarnings;
}
