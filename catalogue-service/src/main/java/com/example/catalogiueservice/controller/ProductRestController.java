package com.example.catalogiueservice.controller;

import com.example.catalogiueservice.moldes.Product;
import com.example.catalogiueservice.record.NewProductPayload;
import com.example.catalogiueservice.serivce.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("catalogue-api/products/{productId:\\d+}")
public class ProductRestController {

    private final ProductService productService;



    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @ModelAttribute("product")
    private Product getProduct(@PathVariable(name = "productId") Long id){
            return productService.findProduct(id).orElseThrow(() -> new NoSuchElementException("данный товар не найден"));
    }

    @GetMapping()
    public Product findProduct(@ModelAttribute("product") Product product){
        return product;
    }


    @PatchMapping()
    public ResponseEntity<?> updateProduct(@PathVariable("productId") Long id, @Valid @RequestBody NewProductPayload newProductPayload,
                                           BindingResult bindingResult)
    throws BindException {
        if (bindingResult.hasErrors()) {
            if(bindingResult instanceof BindException exception){
                throw exception;
            }else {
                throw  new BindException(bindingResult);
            }
        } else {
            productService.updateProduct(id, newProductPayload.title(), newProductPayload.details());
            // метод ничего не возвращает
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping()

    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handlerNoSuchElementException(NoSuchElementException noSuchElementException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                noSuchElementException.getMessage()));
    }
}
