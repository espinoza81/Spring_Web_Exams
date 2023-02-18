package com.resellerapp.model.dtos;

import com.resellerapp.model.entity.Offer;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UserOfferDTO {
    private long id;
    private String description;
    private BigDecimal price;
    private String condition;

    public UserOfferDTO(Offer offer) {
        this.id = offer.getId();
        this.description = offer.getDescription();
        this.price = offer.getPrice();
        this.condition = offer.getCondition().getName().toString();
    }


}
