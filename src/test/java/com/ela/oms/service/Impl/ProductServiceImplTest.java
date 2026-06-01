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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void getAllProductsShouldReturnProducts() {

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Bag");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Shoes");

        List<Product> products = List.of(product1, product2);

        Mockito.when(productRepository.findAll())
                .thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());

        Mockito.verify(productRepository).findAll();
    }

    @Test
    void getProductByIdShouldReturnProduct() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Bag");

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Bag", result.getName());

        Mockito.verify(productRepository).findById(1L);
    }

    @Test
    void getProductByIdShouldThrowException() {

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> productService.getProductById(1L)
        );

        assertEquals("Product Not Found", exception.getMessage());

        Mockito.verify(productRepository).findById(1L);
    }

    @Test
    void updateProductShouldUpdateSuccessfully() {

        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName("Old Bag");

        Product updatedProduct = new Product();
        updatedProduct.setName("New Bag");
        updatedProduct.setPrice(100.0);
        updatedProduct.setQuantity(10);
        updatedProduct.setProductReference(1212);

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(existingProduct));

        Mockito.when(productRepository.save(any(Product.class)))
                .thenReturn(existingProduct);

        Product result = productService.updateProduct(1L, updatedProduct);

        assertNotNull(result);
        assertEquals("New Bag", result.getName());

        Mockito.verify(productRepository).findById(1L);
        Mockito.verify(productRepository).save(any(Product.class));
    }

    @Test
    void deleteProductShouldDeleteSuccessfully() {

        productService.deleteProduct(1L);

        Mockito.verify(productRepository).deleteById(1L);
    }

    @Test
    void mapToEntityShouldReturnProduct() {

        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName("Bag");
        dto.setPrice(100.0);
        dto.setQuantity(5);
        dto.setProductReference(1212);

        Product product = productService.mapToEntity(dto);

        assertNotNull(product);
        assertEquals("Bag", product.getName());
        assertEquals(100.0, product.getPrice());
        assertEquals(5, product.getQuantity());
        assertEquals(1212, product.getProductReference());
    }
}
