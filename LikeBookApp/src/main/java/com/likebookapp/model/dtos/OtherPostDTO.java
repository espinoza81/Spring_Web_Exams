package com.likebookapp.model.dtos;

import com.likebookapp.model.entity.Post;
import com.likebookapp.model.entity.User;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class OtherPostDTO {
    private long id;
    private String username;
    private String content;
    private final Set<Long> userLikesId;
    private String mood;

    public OtherPostDTO(Post post) {
        this.id = post.getId();
        this.content = post.getContent();

        this.userLikesId = post.getUserLikes()
                .stream().map(User::getId)
                .collect(Collectors.toSet());

        this.mood = post.getMood().getMoodName().toString();
        this.username = post.getCreator().getUsername();
    }

    public boolean checkIfUserIdMatchId(Long userId){
        return this.getUserLikesId().stream()
                .anyMatch(userLikeId -> Objects.equals(userLikeId, userId));
    }

    public Set<Long> getUserLikesId() {
        return userLikesId;
    }

    public long getId() {
        return id;
    }

    public OtherPostDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public OtherPostDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getContent() {
        return content;
    }

    public OtherPostDTO setContent(String content) {
        this.content = content;
        return this;
    }

    public String getMood() {
        return mood;
    }

    public OtherPostDTO setMood(String mood) {
        this.mood = mood;
        return this;
    }
}
