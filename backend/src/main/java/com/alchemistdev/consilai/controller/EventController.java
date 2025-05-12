package com.alchemistdev.consilai.controller;

import com.alchemistdev.consilai.dto.EventCreateRequest;
import com.alchemistdev.consilai.dto.EventResponse;
import com.alchemistdev.consilai.model.Event;
import com.alchemistdev.consilai.model.User;
import com.alchemistdev.consilai.service.EventService;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("/create")
    public EventResponse createEvent(@AuthenticationPrincipal UserDetails userDetails,
                             @RequestBody EventCreateRequest request) {
        User user = (User) userDetails;
        return eventService.createEvent(user, request);
    }

    @PostMapping("/event/picture")
    public ResponseEntity<?> uploadEventPicture(
            @RequestParam("eventId") Long eventId,
            @RequestParam("file") MultipartFile file
    ) {
        String fileName = eventService.saveEventPicture(eventId, file);
        return ResponseEntity.ok(Map.of("eventPictureUrl", "/images/events/" + fileName));
    }
}
