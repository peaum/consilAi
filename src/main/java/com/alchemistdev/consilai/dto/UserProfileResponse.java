package com.alchemistdev.consilai.dto;

import com.alchemistdev.consilai.enums.MaritalStatus;

import java.time.LocalDate;

public record UserProfileResponse(
    Long id,
    String email,
    String profilePictureUrl,
    String bio,
    LocalDate birthDate,
    MaritalStatus maritalStatus,
    boolean isVerified,
    boolean isPremium,
    LocalDate premiumExpiration
) {}
