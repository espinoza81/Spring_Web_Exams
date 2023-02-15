package com.example.spotifyplaylistapp.service;

import com.example.spotifyplaylistapp.model.entity.Product;
import com.example.spotifyplaylistapp.model.entity.User;
import com.example.spotifyplaylistapp.repository.ProductRepository;
import com.example.spotifyplaylistapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {


    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public UserService(ProductRepository productRepository,
                       UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

//    public void addSongToPlaylist(Long songId, Long userId) {
//        Product product = productRepository.findById(songId).orElse(null);
//        User user = userRepository.findById(userId).orElse(null);
//
//        Objects.requireNonNull(user).addSong(product);
//
//        userRepository.save(user);
//    }
//
//    public void clearUserPlaylist(Long userId) {
//        User user = userRepository.findById(userId).orElse(null);
//        Objects.requireNonNull(user).clearPlaylist();
//        userRepository.save(user);
//    }
}
