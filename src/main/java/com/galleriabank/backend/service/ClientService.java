package com.galleriabank.backend.service;

import com.galleriabank.backend.domain.Client;
import com.galleriabank.backend.dto.requests.CreateClientRequestDTO;
import com.galleriabank.backend.dto.responses.CursorPaginatedResponseDTO;
import com.galleriabank.backend.dto.responses.GetClientByIdResponseDTO;
import com.galleriabank.backend.dto.responses.ListClientResponseDTO;
import com.galleriabank.backend.exceptions.ClientAlreadyExistsException;
import com.galleriabank.backend.exceptions.ClientDeletedException;
import com.galleriabank.backend.exceptions.ClientNotFoundException;
import com.galleriabank.backend.repository.ClientRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private static final int DEFAULT_LIMIT = 20;
    private static final int MAX_LIMIT = 100;

    private final ClientRepository clientRepository;

    public void create(@NonNull CreateClientRequestDTO body) {

        Optional<Client> optionalClient = this.clientRepository.findByCpf(body.cpf());
        if (optionalClient.isPresent()) {
            throw new ClientAlreadyExistsException();
        }

        Client client = new Client();

        client.setCpf(body.cpf());
        client.setName(body.name());
        client.setPhone(body.phone());

        clientRepository.save(client);
    }


    public GetClientByIdResponseDTO findById(Long id) {
        Optional<Client> optionalClient = this.clientRepository.findById(id);
        if (optionalClient.isEmpty()) {
            throw new ClientNotFoundException();
        }

        Client client = optionalClient.get();
        if (client.getDeletedAt() != null) {
            throw new ClientDeletedException();
        }

        return new GetClientByIdResponseDTO(
                client.getId(),
                client.getName(),
                client.getCpf(),
                client.getPhone()
        );
    }

    public CursorPaginatedResponseDTO<ListClientResponseDTO> list(LocalDateTime cursor, Integer limit) {
        int normalizedLimit = normalizeLimit(limit);
        PageRequest pageRequest = PageRequest.of(0, normalizedLimit + 1);

        List<Client> clients = cursor == null
                ? this.clientRepository.findByDeletedAtIsNullOrderByCreatedAtDesc(pageRequest)
                : this.clientRepository.findByDeletedAtIsNullAndCreatedAtBeforeOrderByCreatedAtDesc(cursor, pageRequest);

        boolean hasNext = clients.size() > normalizedLimit;
        List<ListClientResponseDTO> items = clients.stream()
                .limit(normalizedLimit)
                .map(client -> new ListClientResponseDTO(
                        client.getId(),
                        client.getName(),
                        client.getCpf(),
                        client.getPhone(),
                        client.getCreatedAt()
                ))
                .toList();

        LocalDateTime nextCursor = hasNext ? items.getLast().createdAt() : null;

        return new CursorPaginatedResponseDTO<>(items, nextCursor, hasNext);
    }

    public void update(Long id, String name, String phone) {
        Optional<Client> optionalClient = this.clientRepository.findById(id);
        if (optionalClient.isEmpty()) {
            throw new ClientNotFoundException();
        }

        Client client = optionalClient.get();
        if (client.getDeletedAt() != null) {
            throw new ClientDeletedException();
        }

        if (name != null && !name.isBlank()) {
            client.setName(name);
        }

        if (phone != null && !phone.isBlank()) {
            client.setPhone(phone);
        }

        client.setUpdatedAt(LocalDateTime.now());
        clientRepository.save(client);
    }

    public void delete(Long id) {
        Optional<Client> optionalClient = this.clientRepository.findById(id);
        if (optionalClient.isEmpty()) {
            throw new ClientNotFoundException();
        }

        Client client = optionalClient.get();
        if (client.getDeletedAt() != null) {
            throw new ClientDeletedException();
        }

        client.setDeletedAt(LocalDateTime.now());
        clientRepository.save(client);
    }

    private int normalizeLimit(Integer limit) {
        if (limit == null || limit < 1) {
            return DEFAULT_LIMIT;
        }

        return Math.min(limit, MAX_LIMIT);
    }
}
