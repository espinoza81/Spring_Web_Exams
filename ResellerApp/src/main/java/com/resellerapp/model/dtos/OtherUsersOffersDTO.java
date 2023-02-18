package com.resellerapp.model.dtos;

import com.resellerapp.model.entity.Offer;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
public class OtherUsersOffersDTO {
    private long id;
    private BigDecimal price;
    private String condition;

    private String sellerName;

    private String description;


    public OtherUsersOffersDTO(Offer offer) {
        this.id = offer.getId();
        this.price = offer.getPrice();
        this.condition = offer.getCondition().getName().toString();
        this.description = offer.getDescription();
        this.sellerName = offer.getSeller().getUsername();
    }

}
