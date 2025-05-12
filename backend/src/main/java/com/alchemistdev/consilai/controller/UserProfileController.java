package com.alchemistdev.consilai.controller;

import com.alchemistdev.consilai.dto.UserProfileResponse;
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
    public UserProfileResponse getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        return convertToResponse(profileService.getProfile(user).get());
    }

    @PostMapping
    public UserProfileResponse createProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserProfileUpdateRequest request
    ) {
        User user = (User) userDetails;
        return convertToResponse(profileService.getOrCreateProfile(user, request));
    }

    @PutMapping
    public UserProfileResponse updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserProfileUpdateRequest request
    ) {
        User user = (User) userDetails;
        return convertToResponse(profileService.updateProfile(user, request));
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


    @GetMapping("/has-profile")
    public ResponseEntity<Map<String, Boolean>> hasProfile(@AuthenticationPrincipal User user) {
        boolean exists = profileService.getProfile(user).isPresent();
        return ResponseEntity.ok(Map.of("hasProfile", exists));
    }
    


    private UserProfileResponse convertToResponse(UserProfile profile) {
        return new UserProfileResponse(
                profile.getUser().getId(),
                profile.getUser().getEmail(),
                profile.getProfilePictureUrl(),
                profile.getBio(),
                profile.getBirthDate(),
                profile.getMaritalStatus(),
                profile.isVerified(),
                profile.isPremium(),
                profile.getPremiumExpiration()
        );
    }

}
