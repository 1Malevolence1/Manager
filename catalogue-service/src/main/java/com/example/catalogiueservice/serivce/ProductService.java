package com.example.catalogiueservice.serivce;


import com.example.catalogiueservice.moldes.Product;


import java.util.List;
import java.util.Optional;

public interface ProductService {
    Iterable<Product> findAllProducts(String filter);

    Product createProduct(String title, String details);

    Optional<Product> findProduct(Long productId);

    void updateProduct(Long id, String title, String details);

    void deleteProduct(Long id);
}
