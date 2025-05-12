package com.alchemistdev.consilai.service;

import com.alchemistdev.consilai.dto.LoginRequest;
import com.alchemistdev.consilai.dto.RegisterRequest;
import com.alchemistdev.consilai.model.User;
import com.alchemistdev.consilai.repository.UserRepository;
import com.alchemistdev.consilai.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();

        userRepository.save(user);
        return ResponseEntity.ok("User registered");
    }

    public ResponseEntity<?> login(LoginRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .map(user -> {
                    String token = jwtService.generateToken(user.getEmail(), Map.of("role", user.getRole()));
                    ResponseCookie cookie = ResponseCookie.from("jwt", token)
                            .httpOnly(true)
                            .secure(true)
                            .path("/")
                            .maxAge(24 * 60 * 60)
                            .build();
                    return ResponseEntity.ok()
                            .header("Set-Cookie", cookie.toString())
                            .body("Login successful");
                })
                .orElse(ResponseEntity.status(401).body("Invalid credentials"));
    }
}
