package com.example.coffeeshopapp.service;

import com.example.coffeeshopapp.repository.OrderRepository;
import com.example.coffeeshopapp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public UserService(OrderRepository orderRepository,
                       UserRepository userRepository) {
        this.orderRepository = orderRepository;
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
