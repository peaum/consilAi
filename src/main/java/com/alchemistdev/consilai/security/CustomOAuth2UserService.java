package com.alchemistdev.consilai.security;

import com.alchemistdev.consilai.model.User;
import com.alchemistdev.consilai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        log.info("Cargando usuario de OAuth2: {}", userRequest.getClientRegistration().getClientName());
        OAuth2User oauthUser = new DefaultOAuth2UserService().loadUser(userRequest);
        Map<String, Object> attributes = oauthUser.getAttributes();

        String email = (String) attributes.get("email");
        if (email == null) {
            throw new RuntimeException("Email no disponible desde Google");
        }

        // Buscar o crear el usuario
        userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = User.builder()
                    .email(email)
                    .password("") // No hay password si es login con Google
                    .role("USER")
                    .enabled(true)
                    .build();
            return userRepository.save(newUser);
        });

        return oauthUser;
    }
}
