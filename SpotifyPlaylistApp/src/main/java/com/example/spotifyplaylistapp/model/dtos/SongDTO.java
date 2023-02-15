package com.example.spotifyplaylistapp.model.dtos;


import com.example.spotifyplaylistapp.model.entity.Song;
import lombok.Getter;

@Getter
public class SongDTO {
    private long id;
    private String performer;
    private String title;
    private int duration;

    public SongDTO(Song song) {
        this.id = song.getId();
        this.performer = song.getPerformer();
        this.title = song.getTitle();
        this.duration = song.getDuration();
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%d min)", performer, title, duration);
    }
}
// {performer} - {title} ({duration} min)