package com.alchemistdev.consilai.dto;

import lombok.Data;

import java.time.LocalDate;

import com.alchemistdev.consilai.enums.MaritalStatus;

@Data
public class UserProfileUpdateRequest {
    private String bio;
    private LocalDate birthDate;
    private MaritalStatus maritalStatus;
}