package com.likebookapp.service;

import com.likebookapp.model.dtos.UserLoginDTO;
import com.likebookapp.model.dtos.UserRegistrationDTO;
import com.likebookapp.model.entity.User;
import com.likebookapp.repository.UserRepository;
import com.likebookapp.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoggedUser userSession;

    @Autowired
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       LoggedUser userSession) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userSession = userSession;
    }

    public boolean register(UserRegistrationDTO registrationDTO){
        if(!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())){
            return false;
        }

        Optional<User> byEmail = this.userRepository.findByEmail(registrationDTO.getEmail());
        if(byEmail.isPresent()){
            return false;
        }

        Optional<User> byUsername = this.userRepository.findByUsername(registrationDTO.getUsername());
        if(byUsername.isPresent()){
            return false;
        }

        User user = new User()
                .setEmail(registrationDTO.getEmail())
                .setUsername(registrationDTO.getUsername());

        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));

        this.userRepository.save(user);

        return true;
    }

    public boolean login(UserLoginDTO loginDTO) {
        Optional<User> hasUser = this.userRepository.findByUsername(loginDTO.getUsername());

        if(hasUser.isPresent()) {
            String encodedPassword = hasUser.get().getPassword();
            String rawPassword = loginDTO.getPassword();

            if(passwordEncoder.matches(rawPassword, encodedPassword)) {
                this.userSession.login(hasUser.get());

                return true;
            }

            return false;
        }

        return false;
    }

    public boolean isLogged(){
        return this.userSession.isLogged();
    }

    public void logout() {
        this.userSession.logout();
    }

    public long getLoggedUserId() {
        return this.userSession.getId();
    }
}