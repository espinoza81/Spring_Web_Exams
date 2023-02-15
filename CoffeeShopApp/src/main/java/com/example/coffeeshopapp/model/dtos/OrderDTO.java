package com.example.coffeeshopapp.model.dtos;


import com.example.coffeeshopapp.model.entity.Order;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderDTO {
    private long id;
    private String name;
    private BigDecimal price;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.name = order.getName();
        this.price = order.getPrice();
    }

    @Override
    public String toString() {
        return String.format("Name: %s Price: %.2f lv", name, price);
    }
}
//Name: {name} Price: {price} lv