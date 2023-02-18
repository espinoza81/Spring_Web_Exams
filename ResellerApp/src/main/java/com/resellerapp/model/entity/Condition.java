package com.resellerapp.model.entity;

import com.resellerapp.model.enums.ConditionType;
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
@Table(name = "conditions")
public class Condition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, unique = true)
    private ConditionType name;

    @Column(nullable = false)
    private String description;

    public Condition(ConditionType conditionType) {
        this.name = conditionType;

        switch (conditionType.toString()){
            case "EXCELLENT":
                this.description = "In perfect condition";
                break;
            case "GOOD":
                this.description = "Some signs of wear and tear or minor defects";
                break;
            case "ACCEPTABLE":
                this.description = "The item is fairly worn but continues to function properly";
                break;
            default:
                this.description = "No such condition";
        };
    }
}