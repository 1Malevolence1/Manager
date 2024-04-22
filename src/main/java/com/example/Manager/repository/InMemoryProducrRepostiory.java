package com.example.Manager.repository;

import com.example.Manager.moldes.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository


public class InMemoryProducrRepostiory implements ProductRepository {
    private final List<Product> products = Collections.synchronizedList(new LinkedList<>());
    @Override
    public List<Product> findlAll() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public Product save(Product product) {
        product.setId(this.products.stream()
                .max(Comparator.comparingLong(Product::getId))
                .map(Product::getId)
                .orElse(0l) + 1);
        this.products.add(product);
        return product;
    }
    @Override
    public Optional<Product> findById(Long id) {
        return this.products.stream().filter(prodcut -> Objects.equals(id,prodcut.getId())).findFirst();
    }

    @Override
    public void deletyId(Long id) {
        products.removeIf(product -> Objects.equals(id,product.getId()));
    }
}
