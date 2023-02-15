package com.example.spotifyplaylistapp.service;

import com.example.spotifyplaylistapp.model.entity.Song;
import com.example.spotifyplaylistapp.model.entity.User;
import com.example.spotifyplaylistapp.repository.SongRepository;
import com.example.spotifyplaylistapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class UserService {


    private final SongRepository songRepository;
    private final UserRepository userRepository;

    public UserService(SongRepository songRepository,
                       UserRepository userRepository) {
        this.songRepository = songRepository;
        this.userRepository = userRepository;
    }

    public void addSongToPlaylist(Long songId, Long userId) {
        Song song = songRepository.findById(songId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        Objects.requireNonNull(user).addSong(song);

        userRepository.save(user);
    }

    public void clearUserPlaylist(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        Objects.requireNonNull(user).clearPlaylist();
        userRepository.save(user);
    }
}
