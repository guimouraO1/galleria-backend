package com.galleriabank.backend.service;

import com.galleriabank.backend.domain.User;
import com.galleriabank.backend.dto.requests.CreateUserRequestDTO;
import com.galleriabank.backend.exceptions.UserAlreadyExistsException;
import com.galleriabank.backend.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(@NonNull CreateUserRequestDTO body) {

        Optional<User> optionalUser = this.userRepository.findByLogin(body.login());
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        String encryptedPassword = passwordEncoder.encode(body.password());

        User user = new User();
        user.setLogin(body.login());
        user.setPassword(encryptedPassword);

        userRepository.save(user);
    }
}
