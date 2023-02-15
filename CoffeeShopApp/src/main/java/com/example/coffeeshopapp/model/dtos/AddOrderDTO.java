package com.example.coffeeshopapp.model.dtos;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddOrderDTO {

    @Size(min = 3, max = 20)
    @NotBlank
    private String name;

    @Positive
    @NotNull
    private BigDecimal price;

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime orderTime;

    @NotBlank
    private String category;

    @Size(min = 5)
    @NotBlank
    private String description;
}