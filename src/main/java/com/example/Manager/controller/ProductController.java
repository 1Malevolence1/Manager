package com.example.Manager.controller;



import com.example.Manager.client.BadRequestException;
import com.example.Manager.client.ProductsRestClient;
import com.example.Manager.record.Product;
import com.example.Manager.record.UpdateProductPayload;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("catalogue/products/{productId:\\d+}")
@RequiredArgsConstructor
public class ProductController {

    private final ProductsRestClient productsRestClient;

    @ModelAttribute("product")
    public Product product(@PathVariable("productId") Long productId) {
        return productsRestClient.findProduct(productId).orElseThrow(() -> new NoSuchElementException("Товар не найден"));
    }

    @GetMapping()
    public String getProduct() {
        return "catalogue/products/product";
    }

    @GetMapping("edit")
    public String getProductEditPage() {
        return "catalogue/products/edit";
    }


    @PostMapping("edit")
    public String updateProduct(@ModelAttribute(name = "product", binding = false) Product product,
                                UpdateProductPayload payload,
                                Model model) {
        try {
            this.productsRestClient.updateProduct(product.id(), payload.title(), payload.details());
            return "redirect:/catalogue/products/%d".formatted(product.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "catalogue/products/edit";
        }
    }


    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute("product") Product product){
        productsRestClient.deleteProduct(product.id());
        return "redirect:/catalogue/products/list";
    }


    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException noSuchElementException, Model model,
                                           HttpServletResponse response){
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", noSuchElementException.getMessage());
        return "errors/404";
    }
}
