package com.example.coffeeshopapp.service;

import com.example.coffeeshopapp.model.dtos.AddOrderDTO;
import com.example.coffeeshopapp.model.dtos.EmployeeWithOrderCountDTO;
import com.example.coffeeshopapp.model.dtos.OrderDTO;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<OrderDTO> getAllOrdersByPriceDesc() {

        return this.orderRepository.findAllByOrderByPriceDesc()
                .stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());
    }

    public void orderReady(Long orderId) {
        this.orderRepository.deleteById(orderId);
    }

    public List<EmployeeWithOrderCountDTO> employeeWithOrderCount() {
        return this.orderRepository.getEmployeeWithOrderCountDesc();
    }
}