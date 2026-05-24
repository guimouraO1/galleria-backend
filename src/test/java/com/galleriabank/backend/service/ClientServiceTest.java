package com.galleriabank.backend.service;

import com.galleriabank.backend.domain.Client;
import com.galleriabank.backend.dto.requests.CreateClientRequestDTO;
import com.galleriabank.backend.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    void shouldCreateClient() {
        CreateClientRequestDTO body = new CreateClientRequestDTO("Ana Souza", "12345678901", "11987654321");

        when(clientRepository.findByCpf("12345678901")).thenReturn(Optional.empty());

        clientService.create(body);

        ArgumentCaptor<Client> captor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepository).save(captor.capture());

        assertEquals("Ana Souza", captor.getValue().getName());
        assertEquals("12345678901", captor.getValue().getCpf());
        assertEquals("11987654321", captor.getValue().getPhone());
    }
}
