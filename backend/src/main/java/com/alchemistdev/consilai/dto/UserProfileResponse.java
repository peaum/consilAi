package com.alchemistdev.consilai.dto;

import com.alchemistdev.consilai.enums.MaritalStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record UserProfileResponse(
    Long id,
    String email,
    String profilePictureUrl,
    String bio,
    @Schema(type = "string", pattern = "dd/MM/yyyy HH:mm", example = "20/05/1996")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate birthDate,
    MaritalStatus maritalStatus,
    boolean isVerified,
    boolean isPremium,
    LocalDate premiumExpiration
) {}
