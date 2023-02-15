package com.example.spotifyplaylistapp.repository;

import com.example.spotifyplaylistapp.model.entity.Product;
import com.example.spotifyplaylistapp.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoryAndUserId(Category category, long userId);
    Optional<Product> findByName(String name);

    @Modifying
    void deleteAllByUserId(long userId);
}
