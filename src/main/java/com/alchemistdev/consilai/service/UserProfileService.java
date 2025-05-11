package com.alchemistdev.consilai.service;

import com.alchemistdev.consilai.dto.UserProfileUpdateRequest;
import com.alchemistdev.consilai.model.User;
import com.alchemistdev.consilai.model.UserProfile;
import com.alchemistdev.consilai.repository.UserProfileRepository;
import com.alchemistdev.consilai.util.ImageStorageUtil;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository profileRepository;
    private final ImageStorageUtil imageStorageUtil;
    


    public UserProfile getOrCreateProfile(User user, UserProfileUpdateRequest request) {
        return profileRepository.findByUser(user).orElseGet(() -> {

            
            UserProfile profile = UserProfile.builder()
                    .user(user)
                    .bio(request.getBio())
                    .birthDate(request.getBirthDate())
                    .maritalStatus(request.getMaritalStatus())
                    .isVerified(false)
                    .isPremium(false)
                    .build();
            return profileRepository.save(profile);
        });
    }

    public UserProfile updateProfile(User user, UserProfileUpdateRequest request) {
        UserProfile profile = getOrCreateProfile(user, request);

        profile.setBio(request.getBio());
        profile.setBirthDate(request.getBirthDate());
        profile.setMaritalStatus(request.getMaritalStatus());

        return profileRepository.save(profile);
    }


    public String updateProfilePicture(User user, MultipartFile file) {
        UserProfile profile = getProfile(user).get();

        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get("uploads/profile-pictures", fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            profile.setProfilePictureUrl(fileName);
            profileRepository.save(profile);

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen de perfil", e);
        }
    }

    public Optional<UserProfile> getProfile(User user) {
        return profileRepository.findByUser(user);
    }
}

