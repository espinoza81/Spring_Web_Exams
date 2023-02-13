package com.softuni.battleships.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ships")
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

//    @Column(nullable = false)
    private long health;

//    @Column(nullable = false)
    private long power;

    @Column(nullable = false)
    private LocalDate created;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToOne
    private Category category;

    public Ship() {
    }

    public String getName() {
        return name;
    }

    public Ship setName(String name) {
        this.name = name;
        return this;
    }

    public long getHealth() {
        return health;
    }

    public Ship setHealth(long health) {
        this.health = health;
        return this;
    }

    public long getPower() {
        return power;
    }

    public Ship setPower(long power) {
        this.power = power;
        return this;
    }

    public LocalDate getCreated() {
        return created;
    }

    public Ship setCreated(LocalDate created) {
        this.created = created;
        return this;
    }

    public User getOwner() {
        return owner;
    }

    public Ship setOwner(User owner) {
        this.owner = owner;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Ship setCategory(Category category) {
        this.category = category;
        return this;
    }
}
//Name
//The length of the values must be between 2 and 10 characters (both numbers are INCLUSIVE)

//Health
//The values should be positive numbers

//Power
//The values should be positive numbers

//Created 
//The values should not be future dates

