package com.example.Manager.serivce;

import com.example.Manager.moldes.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAllProducts();

    Product createProduct(String title, String details);

    Optional<Product> findProduct(Long productId);

    void updateProduct(Long id, String title, String details);

    void deleteProduct(Long id);
}
