package com.softuni.battleships.models;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated
    @Column(nullable = false, unique = true)
    private ShipType name;

    @Column(columnDefinition = "text")
    private String description;

    public Category(ShipType name) {
        this.name = name;
    }

    public Category() {

    }
}