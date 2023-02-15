package com.example.spotifyplaylistapp.service;

import com.example.spotifyplaylistapp.model.dtos.AddSongDTO;
import com.example.spotifyplaylistapp.model.dtos.SongDTO;
import com.example.spotifyplaylistapp.model.entity.Song;
import com.example.spotifyplaylistapp.model.entity.Style;
import com.example.spotifyplaylistapp.model.entity.StyleType;
import com.example.spotifyplaylistapp.repository.SongRepository;
import com.example.spotifyplaylistapp.repository.StyleRepository;
import com.example.spotifyplaylistapp.repository.UserRepository;
import com.example.spotifyplaylistapp.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SongService {

    private final UserRepository userRepository;
    private final StyleRepository styleRepository;
//    private final LoggedUser loggedUser;
    private final SongRepository songRepository;

    @Autowired
    public SongService(UserRepository userRepository,
                       StyleRepository styleRepository,
//                       LoggedUser loggedUser,
                       SongRepository songRepository) {
        this.userRepository = userRepository;
        this.styleRepository = styleRepository;
//        this.loggedUser = loggedUser;
        this.songRepository = songRepository;
    }

    public void created(AddSongDTO addSongDTO) {
        StyleType styleType = StyleType.valueOf(addSongDTO.getStyle());
        Style style = this.styleRepository.findByStyleType(styleType);

        Song song = new Song();
        song.setPerformer(addSongDTO.getPerformer());
        song.setTitle(addSongDTO.getTitle());
        song.setDuration(addSongDTO.getDuration());
        song.setStyle(style);
        song.setReleaseDate(addSongDTO.getReleaseDate());


        this.songRepository.save(song);
    }

    public List<SongDTO> getStyleSongs(StyleType styleType) {
        Style style = styleRepository.findByStyleType(styleType);

        return this.songRepository.findAllByStyle(style)
                .stream()
                .map(SongDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<SongDTO> getUserLoggedPlaylist(long loggedUserId) {
        return this.userRepository.findById(loggedUserId)
                .orElseThrow()
                .getPlayList()
                .stream()
                .map(SongDTO::new)
                .collect(Collectors.toList());
    }
//
//    public void likePostWithId(Long postId, Long userId) {
//        Post post = postRepository.findById(postId).orElse(null);
//        User user = userRepository.findById(userId).orElse(null);
//
//        Objects.requireNonNull(post).getUserLikes().add(user);
//
//        postRepository.save(post);
//    }
//
//    public void removePostById(Long postId) {
//        postRepository.deleteById(postId);
//    }
}
