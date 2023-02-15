package com.example.coffeeshopapp.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "order_time", nullable = false)
    private LocalDateTime orderTime;

    @ManyToOne
    private Category category;

    @Column(columnDefinition = "text", nullable = false)
    private String description;

    @ManyToOne
    private User employee;
}