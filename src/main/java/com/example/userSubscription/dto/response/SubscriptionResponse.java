package com.example.userSubscription.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SubscriptionResponse(
        Long id,
        String platformName,
        LocalDate startDate,
        LocalDate endDate) {
}
