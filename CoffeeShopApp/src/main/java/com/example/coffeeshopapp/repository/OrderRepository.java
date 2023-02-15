package com.example.coffeeshopapp.repository;

import com.example.coffeeshopapp.model.entity.Order;
import com.example.coffeeshopapp.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
//    List<Order> findAllByCategoryAndUserId(Category category, long userId);
//    Optional<Order> findByName(String name);
//
//    @Modifying
//    void deleteAllByUserId(long userId);
}
