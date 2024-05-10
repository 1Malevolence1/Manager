package com.example.catalogiueservice.record;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateProductPayload(
        @NotNull
        @Size(min = 3, max = 50)
        String title,
        String details){
}
