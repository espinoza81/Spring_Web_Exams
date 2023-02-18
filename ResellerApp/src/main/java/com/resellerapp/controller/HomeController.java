package com.resellerapp.controller;

import com.resellerapp.model.dtos.OffersOfLoggedDTO;
import com.resellerapp.model.dtos.OtherUsersOffersDTO;
import com.resellerapp.model.dtos.UserOfferDTO;
import com.resellerapp.service.AuthService;
import com.resellerapp.service.OfferService;
import com.resellerapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final AuthService authService;
    private final OfferService offerService;
    private final UserService userService;

    @Autowired
    public HomeController(AuthService authService,
                          OfferService offerService, UserService userService) {
        this.authService = authService;
        this.offerService = offerService;
        this.userService = userService;
    }

    @GetMapping({"/home"})
    public String loggedInIndex(Model model){
        if(!this.authService.isLogged()) {
            return "redirect:/";
        }

        List<UserOfferDTO> userOffers = this.offerService.userOfferList();
        List<OtherUsersOffersDTO> otherOffers = this.offerService.otherUserOfferList();
        List<OffersOfLoggedDTO> loggedOffers = this.userService.getLoggedOffers();

        model.addAttribute("userOffers", userOffers);
        model.addAttribute("otherOffers", otherOffers);
        model.addAttribute("loggedOffers", loggedOffers);



        return "home";
    }

    @GetMapping({"/"})
    public String loggedOutIndex(){
        if(this.authService.isLogged()) {
            return "redirect:/home";
        }
        return "index";
    }

}