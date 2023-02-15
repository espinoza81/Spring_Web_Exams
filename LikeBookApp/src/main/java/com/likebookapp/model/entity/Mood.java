package com.likebookapp.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "moods")
public class Mood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, unique = true)
    private MoodName moodName;

    @Column(columnDefinition = "text")
    private String description;

    public Mood(MoodName moodName) {
        this.moodName = moodName;
    }

    public Mood() {

    }

    public long getId() {
        return id;
    }

    public Mood setId(long id) {
        this.id = id;
        return this;
    }

    public MoodName getMoodName() {
        return moodName;
    }

    public Mood setMoodName(MoodName moodName) {
        this.moodName = moodName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Mood setDescription(String description) {
        this.description = description;
        return this;
    }
}