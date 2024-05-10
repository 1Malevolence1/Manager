package com.example.Manager.client;

import com.example.Manager.record.Product;

import java.util.List;
import java.util.Optional;

public interface ProductsRestClientImpl {

    List<Product> findAllProducts(String filter);

    Product createProduct(String title, String details);

    Optional<Product> findProduct(Long productId);

    void updateProduct(Long id, String title, String details);

    void deleteProduct(Long id);
}
