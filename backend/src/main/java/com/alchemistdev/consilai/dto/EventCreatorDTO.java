package com.alchemistdev.consilai.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EventCreatorDTO {
    private Long id;
    private String email;
    private String bio;
    private LocalDate birthDate;
}
