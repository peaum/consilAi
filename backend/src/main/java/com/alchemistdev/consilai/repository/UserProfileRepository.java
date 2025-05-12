package com.alchemistdev.consilai.repository;

import com.alchemistdev.consilai.model.UserProfile;
import com.alchemistdev.consilai.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUser(User user);
}
