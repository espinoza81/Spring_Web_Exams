package com.resellerapp.model.entity;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "seller")
    private List<Offer> offers;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Offer> boughtOffers;

    public void byeOffer(Offer offer){
        this.boughtOffers.add(offer);
    }
}



//Has Offers
//The offers contains users offers. One user may have many offers and one offer can be created by only one user.
//Has boughtOffers
//Collection with the offers that the user bought. One user may have many offers in boughtOffers.