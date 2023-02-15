package com.likebookapp.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> userLikes;

    @ManyToOne
    private Mood mood;

    public Post(){
        this.userLikes = new HashSet<>();
    }

    public String getContent() {
        return content;
    }

    public Post setContent(String content) {
        this.content = content;
        return this;
    }

    public User getCreator() {
        return creator;
    }

    public Post setCreator(User creator) {
        this.creator = creator;
        return this;
    }

    public Set<User> getUserLikes() {
        return this.userLikes;
    }


    public Mood getMood() {
        return mood;
    }

    public Post setMood(Mood mood) {
        this.mood = mood;
        return this;
    }

    public void addLikes(User user){
        this.userLikes.add(user);
    }

    public long getId() {
        return id;
    }

    public Post setId(long id) {
        this.id = id;
        return this;
    }
}


//Content length must be between 2 and 150 characters (inclusive of 2 and 150).

//The creator of the post. One post can have only one user and one user may have many posts.

//The user likes contains users. One user may like many posts and one post can be liked by many users.

//One post has one mood and one mood can have many posts.