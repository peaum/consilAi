package com.alchemistdev.consilai.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.alchemistdev.consilai.enums.Categories;
import com.alchemistdev.consilai.enums.Cities;
import com.alchemistdev.consilai.enums.MaritalStatus;
import com.alchemistdev.consilai.enums.Gender;


@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imagePath;

    private Categories category;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Integer minAge;
    private Integer maxAge;

    private MaritalStatus maritalStatus;

    @Column(nullable = false)
    private Gender genderTarget;

    private String petType;

    private BigDecimal price;

    @Column(nullable = false)
    private Integer minGroupSize;

    @Column(nullable = false)
    private Integer maxGroupSize;

    private Cities city;

    @Column(nullable = false)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
