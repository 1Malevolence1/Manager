package com.example.Manager.config;

import com.example.Manager.client.ProductsRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {


    @Bean
    public ProductsRestClient productsRestClient(@Value("${selmag.services.catalogue.uri:http://localhost:8080}") String catalogueRestURI){
        return new ProductsRestClient(
                RestClient.builder().baseUrl("http://localhost:8081").build()
        );
    }
}
