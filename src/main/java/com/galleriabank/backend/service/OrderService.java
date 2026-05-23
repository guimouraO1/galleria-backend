package com.galleriabank.backend.service;

import com.galleriabank.backend.domain.Client;
import com.galleriabank.backend.domain.Order;
import com.galleriabank.backend.domain.Product;
import com.galleriabank.backend.dto.requests.CreateOrderRequestDTO;
import com.galleriabank.backend.dto.responses.GetOrderByIdResponseDTO;
import com.galleriabank.backend.dto.responses.GetProductByIdResponseDTO;
import com.galleriabank.backend.exceptions.*;
import com.galleriabank.backend.repository.ClientRepository;
import com.galleriabank.backend.repository.OrderRepository;
import com.galleriabank.backend.repository.ProductRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    public void create(@NonNull CreateOrderRequestDTO body) {
        Optional<Client> optionalClient = this.clientRepository.findById(body.clientId());
        if (optionalClient.isEmpty()) {
            throw new ClientNotFoundException();
        }

        Client client = optionalClient.get();
        if (client.getDeletedAt() != null) {
            throw new ClientDeletedException();
        }

        List<Product> products = productRepository.findAllById(body.productIds());

        Set<Long> missingIds = new HashSet<>(body.productIds());
        products.stream()
                .map(Product::getId)
                .toList()
                .forEach(missingIds::remove);
        if (!missingIds.isEmpty()) {
            throw new ProductNotFoundException("Product(s) with id(s) " + missingIds + " not found");
        }

        List<Long> deletedProductIds = products.stream()
                .filter(product -> product.getDeletedAt() != null)
                .map(Product::getId)
                .toList();
        if (!deletedProductIds.isEmpty()) {
            throw new ProductDeletedException("Product(s) with id(s) " + deletedProductIds + " already deleted");
        }

        Order order = new Order();

        order.setClient(client);
        order.setProducts(products);
        order.setDescription(body.description());
        order.setReferenceCode(body.referenceCode());

        orderRepository.save(order);
    }

    public GetOrderByIdResponseDTO findById(Long id) {
        Optional<Order> optionalOrder = this.orderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            throw new OrderNotFoundException();
        }

        Order order = optionalOrder.get();

        List<GetProductByIdResponseDTO> products = order.getProducts().stream()
                .map(product -> new GetProductByIdResponseDTO(
                        product.getId(),
                        product.getDescription(),
                        product.getValue()
                ))
                .toList();

        BigDecimal total = order.getProducts().stream()
                .map(Product::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new GetOrderByIdResponseDTO(
                order.getId(),
                order.getReferenceCode(),
                order.getDescription(),
                order.getIssuedAt(),
                order.getClient().getId(),
                products,
                total
        );
    }
}
