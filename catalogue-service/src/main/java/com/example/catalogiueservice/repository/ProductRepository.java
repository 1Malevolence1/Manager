package com.example.catalogiueservice.repository;


import com.example.catalogiueservice.moldes.Product;
import org.springframework.data.repository.CrudRepository;


public interface ProductRepository extends CrudRepository<Product, Long> {
    Iterable<Product> findAllByTitleLikeIgnoreCase(String filter);
}
