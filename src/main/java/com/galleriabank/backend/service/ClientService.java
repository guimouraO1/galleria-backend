package com.galleriabank.backend.service;

import com.galleriabank.backend.domain.Client;
import com.galleriabank.backend.dto.requests.CreateClientRequestDTO;
import com.galleriabank.backend.dto.responses.GetClientByIdResponseDTO;
import com.galleriabank.backend.exceptions.ClientAlreadyExistsException;
import com.galleriabank.backend.exceptions.ClientDeletedException;
import com.galleriabank.backend.exceptions.ClientNotFoundException;
import com.galleriabank.backend.repository.ClientRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

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
}
