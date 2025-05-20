package com.example.userSubscription.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;


public record UserRequest(
        @NotBlank(message = "User's name cannot be null or empty")
        String name,
        @NotBlank(message = "User's surname cannot be null or empty")
        String surname,
        @Email(message = "Please provide a valid email address")
        String email,
        LocalDate dateOfBirth
) {
}
