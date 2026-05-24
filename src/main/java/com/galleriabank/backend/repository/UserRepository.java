package com.galleriabank.backend.repository;

import com.galleriabank.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

    Long countByDeletedAtIsNullAndCreatedAtGreaterThanEqualAndCreatedAtBefore(LocalDateTime start, LocalDateTime end);
}