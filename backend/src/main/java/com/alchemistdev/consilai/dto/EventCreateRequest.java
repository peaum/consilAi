package com.alchemistdev.consilai.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Builder.Default;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;

import com.alchemistdev.consilai.enums.Categories;
import com.alchemistdev.consilai.enums.Cities;
import com.alchemistdev.consilai.enums.MaritalStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.alchemistdev.consilai.enums.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;

@Data
@Builder
public class EventCreateRequest {
    private String name;
    private String description;
    //private String imagePath;
    private Categories category;
    @Schema(type = "string", pattern = "dd/MM/yyyy HH:mm", example = "12/05/2025 15:30")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") // deserialización
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime startDate;
    @Schema(type = "string", pattern = "dd/MM/yyyy HH:mm", example = "12/05/2025 15:30")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") // deserialización
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime endDate;
    private Integer minAge;
    private Integer maxAge;
    private MaritalStatus maritalStatus;
    private Gender genderTarget;
    @Nullable
    private String petType;
    @Nullable
    private BigDecimal price;
    @Default
    private Integer minGroupSize = 1;
    @Default
    private Integer maxGroupSize = 1;
    private Cities city;
    private String location;
}
