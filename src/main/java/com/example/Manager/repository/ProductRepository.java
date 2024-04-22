package com.example.Manager.repository;


import com.example.Manager.moldes.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository {

    public List<Product> findlAll();

    public Product save(Product product);

    public Optional<Product> findById(Long id);

    public void deletyId(Long id);


}
