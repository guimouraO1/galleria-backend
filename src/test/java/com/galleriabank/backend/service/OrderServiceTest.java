package com.galleriabank.backend.service;

import com.galleriabank.backend.domain.Client;
import com.galleriabank.backend.domain.Order;
import com.galleriabank.backend.domain.Product;
import com.galleriabank.backend.dto.requests.CreateOrderRequestDTO;
import com.galleriabank.backend.repository.ClientRepository;
import com.galleriabank.backend.repository.OrderRepository;
import com.galleriabank.backend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldCreateOrder() {
        Client client = new Client();
        client.setId(1L);

        Product product = new Product();
        product.setId(1L);
        product.setValue(BigDecimal.valueOf(100));

        CreateOrderRequestDTO body = new CreateOrderRequestDTO("Pedido teste", 1L, List.of(1L), "ORD-001");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(productRepository.findAllById(List.of(1L))).thenReturn(List.of(product));

        orderService.create(body);

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(captor.capture());

        assertEquals(client, captor.getValue().getClient());
        assertEquals(List.of(product), captor.getValue().getProducts());
        assertEquals("Pedido teste", captor.getValue().getDescription());
        assertEquals("ORD-001", captor.getValue().getReferenceCode());
    }
}
