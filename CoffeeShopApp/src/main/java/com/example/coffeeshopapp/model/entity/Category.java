package com.example.coffeeshopapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, unique = true)
    private CategoryType name;

    @Column(name = "needed_time")
    private int neededTime;

    public Category(CategoryType categoryType) {
        this.name = categoryType;
        this.neededTime =
        switch (categoryType.toString()){
            case "Drink" -> 1;
            case "Coffee" -> 2;
            case "Other" -> 5;
            case "Cake" -> 10;
            default -> 0;
        };
    }
}