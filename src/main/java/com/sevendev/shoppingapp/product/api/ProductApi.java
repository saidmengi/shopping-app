package com.sevendev.shoppingapp.product.api;

import com.sevendev.shoppingapp.product.model.ProductResponse;
import com.sevendev.shoppingapp.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductApi {
    private final ProductService productService;

    @GetMapping
    public Flux<ProductResponse> getAllProducts() {
        return productService.getAll();
    }
}
