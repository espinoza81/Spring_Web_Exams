package com.resellerapp.service;

import com.resellerapp.model.dtos.UserLoginDTO;
import com.resellerapp.model.dtos.UserRegistrationDTO;
import com.resellerapp.model.entity.User;
import com.resellerapp.model.helpers.LoggedUser;
import com.resellerapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;


@Component
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

    public boolean register(@Valid UserRegistrationDTO registrationDTO){
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
                .password(passwordEncoder.encode(registrationDTO.getPassword()))
                .email(registrationDTO.getEmail())
                .build();

        this.userRepository.save(user);

        return true;
    }

    public boolean login(@Valid UserLoginDTO loginDTO) {
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