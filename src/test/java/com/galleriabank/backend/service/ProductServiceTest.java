package com.galleriabank.backend.service;

import com.galleriabank.backend.domain.Product;
import com.galleriabank.backend.dto.requests.CreateProductRequestDTO;
import com.galleriabank.backend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldCreateProduct() {
        CreateProductRequestDTO body = new CreateProductRequestDTO("Notebook Dell", BigDecimal.valueOf(4599.90));

        productService.create(body);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(captor.capture());

        assertEquals("Notebook Dell", captor.getValue().getDescription());
        assertEquals(BigDecimal.valueOf(4599.90), captor.getValue().getValue());
    }
}
