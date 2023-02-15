package com.example.spotifyplaylistapp.model.dtos;


import com.example.spotifyplaylistapp.model.entity.Product;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductDTO {
    private long id;
    private String name;
    private BigDecimal price;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
    }

    @Override
    public String toString() {
        return String.format("Name: %s Price: %.2f lv", name, price);
    }
}
//Name: {name} Price: {price} lv