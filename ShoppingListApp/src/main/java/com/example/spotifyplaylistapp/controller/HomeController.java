package com.example.spotifyplaylistapp.controller;

import com.example.spotifyplaylistapp.model.dtos.ProductDTO;
import com.example.spotifyplaylistapp.model.entity.CategoryType;
import com.example.spotifyplaylistapp.service.AuthService;
import com.example.spotifyplaylistapp.service.ProductService;
import com.example.spotifyplaylistapp.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class HomeController {

    private final AuthService authService;
    private final ProductService productService;
    private final LoggedUser loggedUser;

    @Autowired
    public HomeController(AuthService authService,
                          ProductService productService,
                          LoggedUser loggedUser) {
        this.authService = authService;
        this.productService = productService;
        this.loggedUser = loggedUser;
    }

    @GetMapping({"/home"})
    public String loggedInIndex(Model model){
        if(!this.authService.isLogged()) {
            return "redirect:/";
        }

        List<List<ProductDTO>> productsByCategory = this.productService.getAllProductsByCategory();

        BigDecimal priceOfAllProducts =
                productsByCategory
                        .stream()
                        .map(
                                list -> list.stream()
                                        .map(ProductDTO::getPrice)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        ).reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("productsByCategory", productsByCategory);
        model.addAttribute("currentUserId", loggedUser.getId());
        model.addAttribute("priceOfAllProducts", priceOfAllProducts);


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