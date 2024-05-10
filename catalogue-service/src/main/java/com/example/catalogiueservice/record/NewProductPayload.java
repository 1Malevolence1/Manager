package com.example.catalogiueservice.record;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewProductPayload(
        @NotBlank(message = "название не должно быть пустое")
        @Size(min = 3, max = 50)
        String title,
        String details) {

}
