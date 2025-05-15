package com.dev.productserviceapr21capstone.services;

import com.dev.productserviceapr21capstone.dtos.ProductProjection;
import com.dev.productserviceapr21capstone.dtos.ProductProjectionDto;
import com.dev.productserviceapr21capstone.exceptions.ProductNotFoundException;
import com.dev.productserviceapr21capstone.models.Category;
import com.dev.productserviceapr21capstone.models.Product;
import com.dev.productserviceapr21capstone.repositories.CategoryRepository;
import com.dev.productserviceapr21capstone.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("productDbService")
public class ProductDbService implements ProductService{


    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ProductDbService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
        Optional<Product> productOptional =  productRepository.findById(id);
        if(productOptional.isEmpty()){
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        return productOptional.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String name, String description, double price, String imageUrl, String category) {
        Product product = new Product();
        buildProduct(product, name, description, price, imageUrl, category);

        return productRepository.save(product);
    }

    @Override
    public Product replaceProduct(long id, String name, String description, double price, String imageUrl, String category) {
        Product product = new Product();
        product.setId(id);
        buildProduct(product, name, description, price, imageUrl, category);

        return productRepository.save(product);
    }

    private void buildProduct(Product product, String name, String description, double price, String imageUrl, String category) {
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);

        Category categoryObj = getCategoryFromDB(category);
        product.setCategory(categoryObj);
    }

    private Category getCategoryFromDB(String categoryName) {
        Optional<Category> categoryOptional = categoryRepository.findByName(categoryName);
        if(categoryOptional.isPresent()){
            return categoryOptional.get();
        }

        Category newCategory = new Category();
        newCategory.setName(categoryName);

        return categoryRepository.save(newCategory);
    }
}
