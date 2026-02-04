package com.ela.oms.service;

import com.ela.oms.dto.product.ProductRequestDTO;
import com.ela.oms.dto.product.ProductResponseDTO;
import com.ela.oms.entity.product.Product;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO productDTO);

    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);
}
