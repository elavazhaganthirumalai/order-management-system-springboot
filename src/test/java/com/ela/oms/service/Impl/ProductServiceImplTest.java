package com.ela.oms.service.Impl;

import com.ela.oms.dto.product.ProductRequestDTO;
import com.ela.oms.dto.product.ProductResponseDTO;
import com.ela.oms.entity.product.Product;
import com.ela.oms.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productService;
    @Mock
    ProductRepository productRepository;

    @Test
    void createProductShouldCreateProductSuccessfully() {

        ProductRequestDTO productDTO = new ProductRequestDTO();
        productDTO.setProductReference(1212);
        productDTO.setName("Bag");
        Product product = productService.mapToEntity(productDTO);
        product.setId(1L);
        Mockito.when(productRepository.findByProductReference(productDTO.getProductReference())).
                thenReturn(Optional.empty());
        Mockito.when(productRepository.save(any(Product.class))).
                thenReturn(product);
        ProductResponseDTO productResponseDTO =
                productService.createProduct(productDTO);
        System.out.println("my first junit test");
        assertNotNull(productResponseDTO);
        assertEquals(1L, productResponseDTO.getId());
        assertEquals("Bag", productResponseDTO.getName());
        assertEquals(1212, productResponseDTO.getProductReference());
        Mockito.verify(productRepository).findByProductReference(1212);
        Mockito.verify(productRepository).save(any(Product.class));
    }
}
