package com.kaplich.calories.repository;

import com.kaplich.calories.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();

    Product findByProductName(String nameOfProduct);
}
