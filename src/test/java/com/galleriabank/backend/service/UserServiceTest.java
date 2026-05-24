package com.galleriabank.backend.service;

import com.galleriabank.backend.domain.User;
import com.galleriabank.backend.dto.requests.CreateUserRequestDTO;
import com.galleriabank.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUser() {
        CreateUserRequestDTO body = new CreateUserRequestDTO("Guilherme Moura", "guimoura", "myPass@01");

        when(userRepository.findByLogin("guimoura")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("myPass@01")).thenReturn("encrypted");

        userService.create(body);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());

        assertEquals("Guilherme Moura", captor.getValue().getName());
        assertEquals("guimoura", captor.getValue().getLogin());
        assertEquals("encrypted", captor.getValue().getPassword());
    }
}
