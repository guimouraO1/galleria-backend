package com.galleriabank.backend.repository;

import com.galleriabank.backend.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Client> findByCpf(String cpf);
    Optional<Client> findById(Long id);
}
