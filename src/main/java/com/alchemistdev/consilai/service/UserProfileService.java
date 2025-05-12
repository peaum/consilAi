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
            // Sanitiza el nombre original del fichero (solo nombre, sin rutas)
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isBlank()) {
                throw new IllegalArgumentException("El nombre del archivo es inválido");
            }

            String sanitizedFilename = Paths.get(originalFilename).getFileName().toString()
                .replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");

            String fileName = UUID.randomUUID() + "_" + sanitizedFilename;

            // Asegura que el path resultante esté dentro del directorio permitido
            Path uploadDir = Paths.get("uploads/profile-pictures").toAbsolutePath().normalize();
            Files.createDirectories(uploadDir);

            Path targetPath = uploadDir.resolve(fileName).normalize();

            // Validación extra por si acaso
            if (!targetPath.startsWith(uploadDir)) {
                throw new SecurityException("Intento de path traversal detectado");
            }

            Files.write(targetPath, file.getBytes());

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

