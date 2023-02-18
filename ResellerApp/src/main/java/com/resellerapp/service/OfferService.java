package com.resellerapp.service;

import com.resellerapp.model.dtos.AddOfferDTO;
import com.resellerapp.model.dtos.OtherUsersOffersDTO;
import com.resellerapp.model.dtos.UserOfferDTO;
import com.resellerapp.model.entity.Condition;
import com.resellerapp.model.entity.Offer;
import com.resellerapp.model.entity.User;
import com.resellerapp.model.enums.ConditionType;
import com.resellerapp.model.helpers.LoggedUser;
import com.resellerapp.repository.ConditionRepository;
import com.resellerapp.repository.OfferRepository;
import com.resellerapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final UserRepository userRepository;
    private final ConditionRepository conditionRepository;
    private final OfferRepository offerRepository;
    private final LoggedUser loggedUser;

    @Autowired
    public OfferService(UserRepository userRepository,
                        ConditionRepository conditionRepository,
                        OfferRepository offerRepository,
                        LoggedUser loggedUser) {
        this.userRepository = userRepository;
        this.conditionRepository = conditionRepository;
        this.offerRepository = offerRepository;
        this.loggedUser = loggedUser;
    }

    public void created(@Valid AddOfferDTO addOfferDTO) {

        Optional<User> seller = this.userRepository.findById(loggedUser.getId());

        ConditionType conditionType = ConditionType.valueOf(addOfferDTO.getCondition());
        Condition condition = this.conditionRepository.findByName(conditionType);

        Offer offer = Offer.builder()
                .price(addOfferDTO.getPrice())
                .description(addOfferDTO.getDescription())
                .condition(condition)
                .seller(seller.get())
                .build();

        this.offerRepository.save(offer);
    }

    public List<UserOfferDTO> userOfferList() {

        return this.offerRepository.findAllBySellerId(loggedUser.getId())
                .stream()
                .map(UserOfferDTO::new)
                .collect(Collectors.toList());
    }

    public List<OtherUsersOffersDTO> otherUserOfferList() {

        return this.offerRepository.findAllBySellerIdNot(loggedUser.getId())
                .stream()
                .map(OtherUsersOffersDTO::new)
                .collect(Collectors.toList());
    }

    public void offerRemove(Long offerId) {
        this.offerRepository.deleteById(offerId);
    }

    public void offerBuy(Long offerId, Long buyerId) {
        Offer offer = this.offerRepository.findById(offerId).get();

        offer.setSeller(null);

        User user = this.userRepository.findById(buyerId).get();

        user.byeOffer(offer);

        this.offerRepository.save(offer);
        this.userRepository.save(user);
    }
}
