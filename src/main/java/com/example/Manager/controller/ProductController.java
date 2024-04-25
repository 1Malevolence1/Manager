package com.example.Manager.controller;


import com.example.Manager.moldes.Product;
import com.example.Manager.record.UpdateProductPayload;
import com.example.Manager.serivce.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("catalogue/products/{productId:\\d+}")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @ModelAttribute("product")
    public Product product(@PathVariable("productId") Long productId) {
        return productService.findProduct(productId).orElseThrow(() -> new NoSuchElementException("Товар не найден"));
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
                                @Valid UpdateProductPayload payload,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return "catalogue/products/edit";
        } else {
            this.productService.updateProduct(product.getId(), payload.title(), payload.details());
            return "redirect:/catalogue/products/%d".formatted(product.getId());
        }
    }

    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute("product") Product product){
        productService.deleteProduct(product.getId());
        return "redirect:/catalogue/products/list";
    }


    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchFiledException(NoSuchElementException noSuchElementException, Model model,
                                           HttpServletResponse response){
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", noSuchElementException.getMessage());
        return "errors/404";
    }
}
