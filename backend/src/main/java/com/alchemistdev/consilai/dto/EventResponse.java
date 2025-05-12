package com.alchemistdev.consilai.dto;

import com.alchemistdev.consilai.enums.Categories;
import com.alchemistdev.consilai.enums.Gender;
import com.alchemistdev.consilai.enums.MaritalStatus;
import com.alchemistdev.consilai.enums.Cities;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class EventResponse {
    private Long id;
    private String name;
    private String description;
    private String imagePath;
    private Categories category;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer minAge;
    private Integer maxAge;
    private MaritalStatus maritalStatus;
    private Gender genderTarget;
    private String petType;
    private BigDecimal price;
    private Integer minGroupSize;
    private Integer maxGroupSize;
    private Cities city;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private EventCreatorDTO creator;
}
