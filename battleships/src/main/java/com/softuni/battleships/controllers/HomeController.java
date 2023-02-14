package com.softuni.battleships.controllers;

import com.softuni.battleships.models.dtos.ShipDTO;
import com.softuni.battleships.models.dtos.StartBattleDTO;
import com.softuni.battleships.services.AuthService;
import com.softuni.battleships.services.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class HomeController {

    private final AuthService authService;
    private final ShipService shipService;

    @Autowired
    public HomeController(AuthService authService,
                          ShipService shipService) {
        this.authService = authService;
        this.shipService = shipService;
    }

    @ModelAttribute("startBattleDTO")
    public StartBattleDTO initStartBattleDTO(){
        return new StartBattleDTO();
    }

    @GetMapping({"/home"})
    public String loggedInIndex(Model model){
        if(!this.authService.isLogged()) {
            return "redirect:/";
        }

        long loggedUserId = this.authService.getLoggedUserId();

        List<ShipDTO> ownShips = this.shipService.getShipsOwnedBy(loggedUserId);
        List<ShipDTO> enemyShips = this.shipService.getShipsNotOwnedBy(loggedUserId);
        List<ShipDTO> sortedShips = this.shipService.getAllSorted();

        model.addAttribute("ownShips", ownShips);
        model.addAttribute("enemyShips", enemyShips);
        model.addAttribute("sortedShips", sortedShips);
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