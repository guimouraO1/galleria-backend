package com.galleriabank.backend.service;

import com.galleriabank.backend.domain.User;
import com.galleriabank.backend.dto.requests.LoginRequestDTO;
import com.galleriabank.backend.infra.security.AccessTokenService;
import com.galleriabank.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccessTokenService accessTokenService;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldLogin() {
        User user = new User();
        user.setId(1L);
        user.setLogin("guimoura");
        user.setPassword("encrypted");

        when(userRepository.findByLogin("guimoura")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("myPass@01", "encrypted")).thenReturn(true);
        when(accessTokenService.generateToken(user)).thenReturn("token");

        String token = authService.login(new LoginRequestDTO("guimoura", "myPass@01"));

        assertEquals("token", token);
    }
}
