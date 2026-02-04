package com.ela.oms.repository;

import com.ela.oms.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Object> findByProductReference(long productReference);
}