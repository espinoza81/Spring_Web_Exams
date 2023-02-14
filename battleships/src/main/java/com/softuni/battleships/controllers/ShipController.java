package com.softuni.battleships.controllers;

import com.softuni.battleships.models.dtos.CreateShipDTO;
import com.softuni.battleships.models.dtos.UserLoginDTO;
import com.softuni.battleships.services.AuthService;
import com.softuni.battleships.services.ShipService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ShipController {

    private final AuthService authService;
    private final ShipService shipService;

    @ModelAttribute("createShipDTO")
    public CreateShipDTO createShipDTO(){
        return new CreateShipDTO();
    }

    @ModelAttribute("loginDTO")
    public UserLoginDTO initLoginDTO(){
        return new UserLoginDTO();
    }

    @Autowired
    public ShipController(AuthService authService,
                          ShipService shipService) {
        this.authService = authService;
        this.shipService = shipService;
    }

    @GetMapping("/ships")
    public String ships(){
        if(this.authService.isLogged()) {
            return "ship-add";
        }

        return "login";
    }

    @PostMapping("/ships")
    public String register(@Valid CreateShipDTO createShipDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors() || !this.shipService.created(createShipDTO)){
            redirectAttributes.addFlashAttribute("createShipDTO", createShipDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.createShipDTO", bindingResult);

            return "redirect:/ships";
        }

        return "redirect:/home";
    }
}
