package com.resellerapp.service;

import com.resellerapp.model.dtos.OffersOfLoggedDTO;
import com.resellerapp.model.dtos.OtherUsersOffersDTO;
import com.resellerapp.model.helpers.LoggedUser;
import com.resellerapp.repository.OfferRepository;
import com.resellerapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {


    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    private final LoggedUser loggedUser;

    public UserService(OfferRepository offerRepository,
                       UserRepository userRepository, LoggedUser loggedUser) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.loggedUser = loggedUser;
    }

    public List<OffersOfLoggedDTO> getLoggedOffers() {

        return this.userRepository.findById(loggedUser.getId()).get()
                .getBoughtOffers()
                .stream()
                .map(OffersOfLoggedDTO::new)
                .collect(Collectors.toList());
    }
}
