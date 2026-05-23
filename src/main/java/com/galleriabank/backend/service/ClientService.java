package com.galleriabank.backend.service;

import com.galleriabank.backend.domain.Client;
import com.galleriabank.backend.dto.requests.CreateClientRequestDTO;
import com.galleriabank.backend.exceptions.ClientAlreadyExistsException;
import com.galleriabank.backend.repository.ClientRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
