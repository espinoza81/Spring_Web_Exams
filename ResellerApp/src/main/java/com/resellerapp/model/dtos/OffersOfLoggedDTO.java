package com.resellerapp.model.dtos;

import com.resellerapp.model.entity.Offer;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OffersOfLoggedDTO {
    private String description;
    private BigDecimal price;

    public OffersOfLoggedDTO(Offer offer) {
        this.description = offer.getDescription();
        this.price = offer.getPrice();
    }
}
