package com.example.coffeeshopapp.repository;

import com.example.coffeeshopapp.model.entity.Category;
import com.example.coffeeshopapp.model.entity.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(CategoryType categoryType);
}
