package com.alchemistdev.consilai.controller;

import com.alchemistdev.consilai.dto.UserProfileUpdateRequest;
import com.alchemistdev.consilai.model.User;
import com.alchemistdev.consilai.model.UserProfile;
import com.alchemistdev.consilai.service.UserProfileService;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService profileService;

    @GetMapping
    public UserProfile getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        return profileService.getOrCreateProfile(user);
    }

    @PutMapping
    public UserProfile updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserProfileUpdateRequest request
    ) {
        User user = (User) userDetails;
        return profileService.updateProfile(user, request);
    }

    @PostMapping("/picture")
    public ResponseEntity<?> uploadProfilePicture(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("file") MultipartFile file
    ) {
        User user = (User) userDetails;
        String fileName = profileService.updateProfilePicture(user, file);
        return ResponseEntity.ok(Map.of("profilePictureUrl", "/images/profile/" + fileName));
    }
}
