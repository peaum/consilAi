package com.alchemistdev.consilai.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserProfileUpdateRequest {
    private String bio;
    private LocalDate birthDate;
    private String maritalStatus;
}