package com.example.Manager.repository;

import com.example.Manager.moldes.Product;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.LongStream;

@Repository
public class InMemoryProducerRepository implements ProductRepository {
    private static final List<Product> products = Collections.synchronizedList(new LinkedList<>());


    public InMemoryProducerRepository() {
        LongStream.range(1, 4).forEach(item -> products.add(new Product(
                item,
                "Товар №%d".formatted(item),
                "Описание товара №%d".formatted(item))));
    }

    @Override
    public List<Product> findAll() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public Product save(Product product) {
        product.setId(products.stream()
                .max(Comparator.comparingLong(Product::getId))
                .map(Product::getId)
                .orElse(0l) + 1);
        products.add(product);
        return product;
    }
    @Override
    public Optional<Product> findById(Long id) {
        return this.products.stream().filter(prodcut -> Objects.equals(id,prodcut.getId())).findFirst();
    }

    @Override
    public void deleteId(Long id) {
        products.removeIf(product -> Objects.equals(id,product.getId()));
    }
}
