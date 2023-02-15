package com.likebookapp.model.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostCreatedDto {

    @Size(min = 2, max = 150)
    @NotBlank
    private String content;

    @NotNull
    private String mood;

    public PostCreatedDto() {
    }

    public String getContent() {
        return content;
    }

    public PostCreatedDto setContent(String content) {
        this.content = content;
        return this;
    }

    public String getMood() {
        return mood;
    }

    public PostCreatedDto setMood(String mood) {
        this.mood = mood;
        return this;
    }
}
