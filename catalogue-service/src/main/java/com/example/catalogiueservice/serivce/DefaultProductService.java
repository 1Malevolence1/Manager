package com.example.catalogiueservice.serivce;


import com.example.catalogiueservice.moldes.Product;
import com.example.catalogiueservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Iterable<Product> findAllProducts(String filter) {
        if(filter != null && !filter.isBlank()){
            return productRepository.findAllByTitleLikeIgnoreCase("%" + filter + "%");
        } else return productRepository.findAll();
    }

    @Override
    public Product createProduct(String title, String details) {
        return this.productRepository.save(new Product(null, title, details));
    }

    @Override
    public Optional<Product> findProduct(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public void  updateProduct(Long id, String title, String details) {
        this.productRepository.findById(id)
                .ifPresentOrElse(product -> {
                    product.setTitle(title);
                    product.setDetails(details);
                    productRepository.save(product);
                },
                        () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    public void deleteProduct(Long id) {
        this.productRepository.deleteById(id);
    }
}