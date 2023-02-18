package com.resellerapp.controller;

import com.resellerapp.model.dtos.AddOfferDTO;
import com.resellerapp.model.helpers.LoggedUser;
import com.resellerapp.service.AuthService;
import com.resellerapp.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class OfferController {

    private final OfferService offerService;
    private final AuthService authService;

    private final LoggedUser loggedUser;


    public OfferController(OfferService offerService,
                           AuthService authService,
                           LoggedUser loggedUser) {
        this.offerService = offerService;
        this.authService = authService;
        this.loggedUser = loggedUser;
    }

    @ModelAttribute("addOfferDTO")
    public AddOfferDTO initAddOfferDTO(){
        return new AddOfferDTO();
    }

    @GetMapping("/offer/add")
    public String product(){
        if(this.authService.isLogged()) {
            return "offer-add";
        }
        return "redirect:/login";
    }

    @PostMapping("/offer/add")
    public String addOrder(@Valid AddOfferDTO addOfferDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        if(!this.authService.isLogged()) {
            return "redirect:/";
        }

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addOfferDTO", addOfferDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.addOfferDTO", bindingResult);

            return "redirect:/offer/add";
        }

        this.offerService.created(addOfferDTO);

        return "redirect:/home";
    }

    @GetMapping("offer/remove/{id}")
    String orderReady(@PathVariable Long id){
        if(!this.authService.isLogged()) {
            return "redirect:/";
        }

        offerService.offerRemove(id);
        return "redirect:/home";
    }

    @GetMapping("/offer/bue/{id}")
    String orderBuy(@PathVariable Long id){
        if(!this.authService.isLogged()) {
            return "redirect:/";
        }

        offerService.offerBuy(id, loggedUser.getId());
        return "redirect:/home";
    }
}
