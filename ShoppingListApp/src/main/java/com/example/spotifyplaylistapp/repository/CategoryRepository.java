package com.example.spotifyplaylistapp.repository;

import com.example.spotifyplaylistapp.model.entity.Category;
import com.example.spotifyplaylistapp.model.entity.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryType(CategoryType categoryType);
}
