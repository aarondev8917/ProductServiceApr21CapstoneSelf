package com.dev.productserviceapr21capstone.services;

import com.dev.productserviceapr21capstone.dtos.CreateFakeStoreProductRequestDto;
import com.dev.productserviceapr21capstone.exceptions.ProductNotFoundException;
import com.dev.productserviceapr21capstone.models.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(long id) throws ProductNotFoundException;
    List<Product> getAllProducts();
    Product createProduct(String name, String description, double price, String imageUrl, String category);
    Product replaceProduct(long id, String name, String description, double price, String imageUrl, String category);

}
