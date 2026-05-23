package com.galleriabank.backend.service;

import com.galleriabank.backend.domain.User;
import com.galleriabank.backend.dto.requests.LoginRequestDTO;
import com.galleriabank.backend.exceptions.InvalidCredentialsException;
import com.galleriabank.backend.exceptions.UserDeletedException;
import com.galleriabank.backend.exceptions.UserNotFoundException;
import com.galleriabank.backend.infra.security.AccessTokenService;
import com.galleriabank.backend.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenService accessTokenService;

    public String login(@NonNull LoginRequestDTO body) {

        Optional<User> optionalUser = this.userRepository.findByLogin(body.login());
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = optionalUser.get();
        if (user.getDeletedAt() != null) {
            throw new UserDeletedException();
        }

        if (!passwordEncoder.matches(body.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return this.accessTokenService.generateToken(user);
    }
}
