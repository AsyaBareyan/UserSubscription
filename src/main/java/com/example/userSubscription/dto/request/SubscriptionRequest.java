package com.example.userSubscription.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SubscriptionRequest(

        @NotEmpty(message = "Platform name cannot be empty")
        String platformName,
        @NotNull(message = "Start date cannot be null")
        LocalDate startDate,
        @NotNull(message = "End date cannot be null")
        LocalDate endDate
) {
}
