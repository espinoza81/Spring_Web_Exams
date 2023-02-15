package com.example.spotifyplaylistapp.init;

import com.example.spotifyplaylistapp.model.entity.Category;
import com.example.spotifyplaylistapp.model.entity.CategoryType;
import com.example.spotifyplaylistapp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategorySeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategorySeeder(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        if(this.categoryRepository.count() == 0) {

            List<Category> categories = Arrays.stream(CategoryType.values())
                    .map(Category::new).toList();

            this.categoryRepository.saveAll(categories);
        }
    }
}
