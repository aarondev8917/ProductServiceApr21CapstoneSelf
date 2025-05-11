package com.dev.productserviceapr21capstone.repositories;

import com.dev.productserviceapr21capstone.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String category);
    Category save(Category category);
}
