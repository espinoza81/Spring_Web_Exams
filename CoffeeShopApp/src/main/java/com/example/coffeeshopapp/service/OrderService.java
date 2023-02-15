package com.example.coffeeshopapp.service;

import com.example.coffeeshopapp.model.dtos.AddOrderDTO;
import com.example.coffeeshopapp.model.entity.Category;
import com.example.coffeeshopapp.model.entity.Order;
import com.example.coffeeshopapp.model.entity.CategoryType;
import com.example.coffeeshopapp.model.entity.User;
import com.example.coffeeshopapp.repository.OrderRepository;
import com.example.coffeeshopapp.repository.CategoryRepository;
import com.example.coffeeshopapp.repository.UserRepository;
import com.example.coffeeshopapp.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;
    private final LoggedUser loggedUser;

    @Autowired
    public OrderService(UserRepository userRepository,
                        CategoryRepository categoryRepository,
                        OrderRepository orderRepository,
                        LoggedUser loggedUser) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.orderRepository = orderRepository;
        this.loggedUser = loggedUser;
    }

    public void created(AddOrderDTO addOrderDTO) {

        Optional<User> employee = this.userRepository.findById(loggedUser.getId());

        CategoryType categoryType = CategoryType.valueOf(addOrderDTO.getCategory());
        Category category = this.categoryRepository.findByName(categoryType);

        Order order = Order.builder()
                .name(addOrderDTO.getName())
                .price(addOrderDTO.getPrice())
                .orderTime(addOrderDTO.getOrderTime())
                .category(category)
                .description(addOrderDTO.getDescription())
                .employee(employee.get())
                .build();

        this.orderRepository.save(order);
    }

//    public List<OrderDTO> getProductsByCategory(CategoryType categoryType) {
//        Category category = categoryRepository.findByCategoryType(categoryType);
//
//        return this.orderRepository.findAllByCategoryAndUserId(category, loggedUser.getId())
//                .stream()
//                .map(OrderDTO::new)
//                .collect(Collectors.toList());
//    }
//
//    public List<List<OrderDTO>> getAllProductsByCategory() {
//        List<List<OrderDTO>> productsByCategory = new ArrayList<>();
//
//        List<OrderDTO> food = this.getProductsByCategory(CategoryType.Food);
//        List<OrderDTO> drink = this.getProductsByCategory(CategoryType.Drink);
//        List<OrderDTO> household = this.getProductsByCategory(CategoryType.Household);
//        List<OrderDTO> other = this.getProductsByCategory(CategoryType.Other);
//
//        productsByCategory.add(food);
//        productsByCategory.add(drink);
//        productsByCategory.add(household);
//        productsByCategory.add(other);
//
//        return productsByCategory;
//    }
//
//    @Transactional
//    public void buyAllProduct(Long userId) {
//        this.orderRepository.deleteAllByUserId(userId);
//    }
//
//    public void buyProduct(Long productId) {
//        this.orderRepository.deleteById(productId);
//    }

}