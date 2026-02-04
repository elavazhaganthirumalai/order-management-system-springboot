package com.ela.oms.service.Impl;

import com.ela.oms.dto.product.ProductRequestDTO;
import com.ela.oms.dto.product.ProductResponseDTO;
import com.ela.oms.entity.product.Product;
import com.ela.oms.exception.DuplicateResourceException;
import com.ela.oms.repository.ProductRepository;
import com.ela.oms.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productDTO) {
        Product product = mapToEntity(productDTO);
        if (productRepository.findByProductReference(product.getProductReference()).isPresent()) {
            throw new DuplicateResourceException("Product Reference already exists");
        }
        return mapToResponseDTO(productRepository.save(product));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not Found"));
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product existing = getProductById(id);
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setQuantity(product.getQuantity());
        existing.setProductReference(product.getProductReference());
        return productRepository.save(existing);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product mapToEntity(ProductRequestDTO dto) {
        if (dto == null) return null;

        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setProductReference(dto.getProductReference());

        return product;
    }

    public ProductResponseDTO mapToResponseDTO(Product product) {
        if (product == null) return null;

        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setProductReference(product.getProductReference());

        return dto;
    }
}
