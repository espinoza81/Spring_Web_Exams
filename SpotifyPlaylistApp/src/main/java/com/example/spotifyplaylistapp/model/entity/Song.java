package com.example.spotifyplaylistapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String performer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int duration;

    @ManyToOne
    private Style style;

    @Column(name = "release_date")
    private LocalDate releaseDate;
}


//Performer name length must be between 3 and 20 characters (inclusive of 3 and 20).

//Title length must be between 2 and 20 characters (inclusive of 2 and 20).

//The duration (in seconds) must be a positive number

//The Date that cannot be in the future

