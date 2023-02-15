package com.example.coffeeshopapp.service;


import com.example.coffeeshopapp.model.dtos.UserLoginDTO;
import com.example.coffeeshopapp.model.dtos.UserRegistrationDTO;
import com.example.coffeeshopapp.model.entity.User;
import com.example.coffeeshopapp.repository.UserRepository;
import com.example.coffeeshopapp.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoggedUser loggedUser;

    @Autowired
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       LoggedUser loggedUser) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loggedUser = loggedUser;
    }

    public boolean register(UserRegistrationDTO registrationDTO){
        if(!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())){
            return false;
        }

        Optional<User> byUsername = this.userRepository.findByUsername(registrationDTO.getUsername());
        if(byUsername.isPresent()){
            return false;
        }

        Optional<User> byEmail = this.userRepository.findByEmail(registrationDTO.getEmail());
        if(byEmail.isPresent()){
            return false;
        }

        User user = User.builder()
                .username(registrationDTO.getUsername())
                .firstName(registrationDTO.getFirstName())
                .lastName(registrationDTO.getLastName())
                .password(passwordEncoder.encode(registrationDTO.getPassword()))
                .email(registrationDTO.getEmail())
                .build();

        this.userRepository.save(user);

        return true;
    }

    public boolean login(UserLoginDTO loginDTO) {
        Optional<User> hasUser = this.userRepository.findByUsername(loginDTO.getUsername());

        if(hasUser.isPresent()) {
            String encodedPassword = hasUser.get().getPassword();
            String rawPassword = loginDTO.getPassword();

            if(passwordEncoder.matches(rawPassword, encodedPassword)) {
                this.loggedUser.login(hasUser.get());

                return true;
            }

            return false;
        }

        return false;
    }

    public boolean isLogged(){
        return this.loggedUser.isLogged();
    }

    public void logout() {
        this.loggedUser.logout();
    }

    public long getLoggedUserId() {
        return this.loggedUser.getId();
    }
}