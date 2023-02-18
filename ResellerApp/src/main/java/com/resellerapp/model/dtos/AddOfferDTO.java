package com.resellerapp.model.dtos;

import lombok.*;


import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddOfferDTO {

    @Size(min = 2, max = 50)
    @NotBlank
    private String description;

    @Positive
    @NotNull
    private BigDecimal price;


    @NotBlank
    private String condition;

}