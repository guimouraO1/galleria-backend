package com.galleriabank.backend.repository;

import com.galleriabank.backend.domain.Client;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByCpf(String cpf);

    List<Client> findByDeletedAtIsNullOrderByCreatedAtDesc(Pageable pageable);

    List<Client> findByDeletedAtIsNullAndCreatedAtBeforeOrderByCreatedAtDesc(LocalDateTime cursor, Pageable pageable);
}
