package com.example.userSubscription.dto.response;

import java.time.LocalDate;
import java.util.List;

public record UserResponse(
        Long id,
        String name,
        String surname,
        String email,
        LocalDate dateOfBirth,
        List<SubscriptionResponse> subscriptions
) {
}
