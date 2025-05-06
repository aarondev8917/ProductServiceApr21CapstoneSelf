package com.dev.productserviceapr21capstone.controllers;

import com.dev.productserviceapr21capstone.dtos.CreateFakeStoreProductRequestDto;
import com.dev.productserviceapr21capstone.dtos.ErrorDto;
import com.dev.productserviceapr21capstone.dtos.ProductResponseDto;
import com.dev.productserviceapr21capstone.exceptions.ProductNotFoundException;
import com.dev.productserviceapr21capstone.models.Product;
import com.dev.productserviceapr21capstone.services.FakeStoreProductService;
import com.dev.productserviceapr21capstone.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("products/{id}")
    public ProductResponseDto getProductById(@PathVariable("id") long id) throws ProductNotFoundException {
        Product  product = productService.getProductById(id);

        return ProductResponseDto.from(product);

    }

    @GetMapping("/products")
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productService.getAllProducts();

//      List<ProductResponseDto> productResponseDtos = products.stream().map(ProductResponseDto::from).collect(Collectors.toList());

        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product product : products) {
            ProductResponseDto productResponseDto = ProductResponseDto.from(product);
            productResponseDtos.add(productResponseDto);

        }

        return productResponseDtos;
    }


    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody CreateFakeStoreProductRequestDto createFakeStoreProductRequestDto) {
        Product product = productService.createProduct(createFakeStoreProductRequestDto.getName(),
                createFakeStoreProductRequestDto.getDescription(),
                createFakeStoreProductRequestDto.getPrice(),
                createFakeStoreProductRequestDto.getImageUrl(),
                createFakeStoreProductRequestDto.getCategory());
        return new ResponseEntity<>(ProductResponseDto.from(product), HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    public ProductResponseDto replaceProduct(@PathVariable("id") long id,@RequestBody CreateFakeStoreProductRequestDto createFakeStoreProductRequestDto) {
        Product product = productService.replaceProduct(id,
                createFakeStoreProductRequestDto.getName(),
                createFakeStoreProductRequestDto.getDescription(),
                createFakeStoreProductRequestDto.getPrice(),
                createFakeStoreProductRequestDto.getImageUrl(),
                createFakeStoreProductRequestDto.getCategory()
        );

        return ProductResponseDto.from(product);
    }
}