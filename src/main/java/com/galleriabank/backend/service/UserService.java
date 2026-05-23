package com.galleriabank.backend.service;

import com.galleriabank.backend.domain.User;
import com.galleriabank.backend.dto.requests.CreateUserRequestDTO;
import com.galleriabank.backend.dto.responses.GetUserByIdResponseDTO;
import com.galleriabank.backend.exceptions.UserAlreadyExistsException;
import com.galleriabank.backend.exceptions.UserDeletedException;
import com.galleriabank.backend.exceptions.UserNotFoundException;
import com.galleriabank.backend.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
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
        user.setName(body.name());
        user.setPassword(encryptedPassword);

        userRepository.save(user);
    }

    public GetUserByIdResponseDTO findById(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = optionalUser.get();
        if (user.getDeletedAt() != null) {
            throw new UserDeletedException();
        }

        return new GetUserByIdResponseDTO(
                user.getId(),
                user.getName(),
                user.getLogin()
        );
    }

    public void update(Long id, String name, String password) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = optionalUser.get();
        if (user.getDeletedAt() != null) {
            throw new UserDeletedException();
        }

        if (name != null && !name.isBlank()) {
            user.setName(name);
        }

        if (password != null && !password.isBlank()) {
            user.setPassword(passwordEncoder.encode(password));
        }

        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public void delete(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = optionalUser.get();
        if (user.getDeletedAt() != null) {
            throw new UserDeletedException();
        }

        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}
