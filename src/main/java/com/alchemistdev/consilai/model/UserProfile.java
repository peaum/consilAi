package com.alchemistdev.consilai.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

import com.alchemistdev.consilai.enums.MaritalStatus;

@Entity
@Table(name = "user_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String profilePictureUrl;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private LocalDate birthDate;

    private MaritalStatus maritalStatus;

    private boolean isVerified;

    private boolean isPremium;

    private LocalDate premiumExpiration;
}
