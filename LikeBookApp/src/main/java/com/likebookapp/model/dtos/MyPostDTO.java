package com.likebookapp.model.dtos;

import com.likebookapp.model.entity.Post;

public class MyPostDTO {
    private long id;
    private String content;
    private int likes;
    private String mood;

    public MyPostDTO(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.likes = post.getUserLikes().size();
        this.mood = post.getMood().getMoodName().toString();
    }

    public long getId() {
        return id;
    }

    public MyPostDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MyPostDTO setContent(String content) {
        this.content = content;
        return this;
    }

    public int getLikes() {
        return likes;
    }

    public MyPostDTO setLikes(int likes) {
        this.likes = likes;
        return this;
    }

    public String getMood() {
        return mood;
    }

    public MyPostDTO setMood(String mood) {
        this.mood = mood;
        return this;
    }
}
