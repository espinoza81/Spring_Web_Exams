package com.example.spotifyplaylistapp.service;

import com.example.spotifyplaylistapp.model.dtos.AddProductDTO;
import com.example.spotifyplaylistapp.model.dtos.ProductDTO;
import com.example.spotifyplaylistapp.model.entity.Category;
import com.example.spotifyplaylistapp.model.entity.Product;
import com.example.spotifyplaylistapp.model.entity.CategoryType;
import com.example.spotifyplaylistapp.model.entity.User;
import com.example.spotifyplaylistapp.repository.ProductRepository;
import com.example.spotifyplaylistapp.repository.CategoryRepository;
import com.example.spotifyplaylistapp.repository.UserRepository;
import com.example.spotifyplaylistapp.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final LoggedUser loggedUser;

    @Autowired
    public ProductService(UserRepository userRepository,
                          CategoryRepository categoryRepository,
                          ProductRepository productRepository,
                          LoggedUser loggedUser) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.loggedUser = loggedUser;
    }

    public boolean created(AddProductDTO addProductDTO) {

        Optional<Product> hasProductWithName = productRepository.findByName(addProductDTO.getName());
        if(hasProductWithName.isPresent()){
            return false;
        }

        Optional<User> user = this.userRepository.findById(loggedUser.getId());

        if(user.isEmpty()){
            return false;
        }

        CategoryType categoryType = CategoryType.valueOf(addProductDTO.getCategory());
        Category category = this.categoryRepository.findByCategoryType(categoryType);

        Product product = new Product();
        product.setName(addProductDTO.getName());
        product.setDescription(addProductDTO.getDescription());
        product.setNeededBefore(addProductDTO.getNeededBefore());
        product.setCategory(category);
        product.setPrice(addProductDTO.getPrice());
        product.setUser(user.get());


        this.productRepository.save(product);
        return true;
    }

    public List<ProductDTO> getProductsByCategory(CategoryType categoryType) {
        Category category = categoryRepository.findByCategoryType(categoryType);

        return this.productRepository.findAllByCategoryAndUserId(category, loggedUser.getId())
                .stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }

    public List<List<ProductDTO>> getAllProductsByCategory() {
        List<List<ProductDTO>> productsByCategory = new ArrayList<>();

        List<ProductDTO> food = this.getProductsByCategory(CategoryType.Food);
        List<ProductDTO> drink = this.getProductsByCategory(CategoryType.Drink);
        List<ProductDTO> household = this.getProductsByCategory(CategoryType.Household);
        List<ProductDTO> other = this.getProductsByCategory(CategoryType.Other);

        productsByCategory.add(food);
        productsByCategory.add(drink);
        productsByCategory.add(household);
        productsByCategory.add(other);

        return productsByCategory;
    }

    @Transactional
    public void buyAllProduct(Long userId) {
        this.productRepository.deleteAllByUserId(userId);
    }

    public void buyProduct(Long productId) {
        this.productRepository.deleteById(productId);
    }

}