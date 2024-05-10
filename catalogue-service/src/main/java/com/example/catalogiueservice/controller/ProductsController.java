package com.example.catalogiueservice.controller;

import com.example.catalogiueservice.moldes.Product;
import com.example.catalogiueservice.record.NewProductPayload;
import com.example.catalogiueservice.serivce.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("catalogue-api/products")
public class ProductsController {

    private final ProductService productService;

    @Autowired
    public ProductsController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public Iterable<Product> findProducts(@RequestParam(name = "filter", required = false) String filter) {
        return productService.findAllProducts(filter);
    }


    @PostMapping

    public ResponseEntity<?> createProduct(@Valid @RequestBody NewProductPayload newProductPayload,
                                           BindingResult bindingResult,
                                           UriComponentsBuilder uriComponentsBuilder) throws BindException {

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Product product = this.productService.createProduct(newProductPayload.title(), newProductPayload.details());
            // В метод created() нужно передать ссылку на созданный объект
            /*
1. ResponseEntity.created(...): Это статический метод класса ResponseEntity, который создает объект ResponseEntity с HTTP-статусом 201 (Created).
2. uriComponentsBuilder: Это объект, который используется для построения URI компонентов. Он позволяет манипулировать частями URI, такими как пути, параметры и т.д.
3. replacePath(...): Метод replacePath объекта UriComponentsBuilder используется для замены части пути в URI. В данном случае путь /catalogue-api/products/{productId} заменяется на новый путь с использованием идентификатора продукта.
4. build(...): После того как все необходимые изменения внесены в URI, метод build преобразует его в строку.
5. Map.of("productId", product.getDetails()): Здесь создается ассоциативный массив (в Java 9+), который содержит ключ "productId" и значение, полученное из метода getDetails() объекта product. Этот массив затем передается в метод build для замены шаблона {productId} в URI.
6. uriComponentsBuilder.build(...): Этот метод возвращает строку с новым URI.
7. .body(product): Метод body устанавливает тело ответа. В данном случае в качестве тела используется объект product.

             */
            return ResponseEntity.created(uriComponentsBuilder.
                    replacePath("/catalogue-api/products/{productId}")
                    .build(Map.of("productId", product.getId())))
                    .body(product);
        }
    }



}
