package com.alchemistdev.consilai.service;

import com.alchemistdev.consilai.model.Event;
import com.alchemistdev.consilai.model.User;
import com.alchemistdev.consilai.model.UserProfile;
import com.alchemistdev.consilai.repository.EventRepository;
import com.alchemistdev.consilai.repository.UserProfileRepository;

import jakarta.persistence.EntityNotFoundException;

import com.alchemistdev.consilai.enums.Gender;
import com.alchemistdev.consilai.dto.EventCreateRequest;
import com.alchemistdev.consilai.dto.EventCreatorDTO;
import com.alchemistdev.consilai.dto.EventResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final EventRepository eventRepository;
    private final UserProfileRepository userProfileRepository;

    public EventResponse createEvent(User user, EventCreateRequest request) {

        log.warn("Creando evento para el usuario: {}", user.getUsername());

        var profile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        validatePremiumAccess(profile, request.getPrice());
        validateGenderRules(user, request.getGenderTarget());
        
        Event event = buildEventFromRequest(user, request);
        event = eventRepository.save(event);

        return convertToDto(event);
    }

    public String saveEventPicture(Long eventId, MultipartFile file) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado con ID: " + eventId));

        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get("uploads/events-pictures", fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            event.setImagePath(fileName);
            eventRepository.save(event);

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen del evento", e);
        }
    }



    private EventResponse convertToDto(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .imagePath(event.getImagePath())
                .category(event.getCategory())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .minAge(event.getMinAge())
                .maxAge(event.getMaxAge())
                .maritalStatus(event.getMaritalStatus())
                .genderTarget(event.getGenderTarget())
                .petType(event.getPetType())
                .price(event.getPrice())
                .minGroupSize(event.getMinGroupSize())
                .maxGroupSize(event.getMaxGroupSize())
                .city(event.getCity())
                .location(event.getLocation())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .creator(EventCreatorDTO.builder()
                        .id(event.getCreator().getId())
                        .email(event.getCreator().getEmail())
                        .bio(event.getCreator().getProfile().getBio())
                        .birthDate(event.getCreator().getProfile().getBirthDate())
                        .build())
                .build();
    }


    private void validatePremiumAccess(UserProfile profile, BigDecimal price) {
        if (price != null && !profile.isPremium()) {
            throw new IllegalStateException("No eres premium, no puedes establecer un precio en el evento.");
        }
    }

    private void validateGenderRules(User user, Gender targetGender) {
        Gender userGender = user.getProfile().getGender();

        if (targetGender != null && !targetGender.equals(Gender.NO_ESPECIFICADO)) {
            if (!targetGender.equals(userGender)) {
                throw new IllegalStateException("El género del evento no coincide con el género del creador.");
            }
            if (targetGender == Gender.MUJER && !user.getProfile().isVerified()) {
                throw new IllegalStateException("El género del evento es femenino, pero el perfil no está verificado.");
            }
        }
    }


    private Event buildEventFromRequest(User user, EventCreateRequest request) {
        return Event.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .minAge(request.getMinAge())
                .maxAge(request.getMaxAge())
                .maritalStatus(request.getMaritalStatus())
                .genderTarget(request.getGenderTarget())
                .petType(request.getPetType())
                .price(request.getPrice())
                .minGroupSize(request.getMinGroupSize())
                .maxGroupSize(request.getMaxGroupSize())
                .city(request.getCity())
                .location(request.getLocation())
                .creator(user)
                .build();
    }


}
