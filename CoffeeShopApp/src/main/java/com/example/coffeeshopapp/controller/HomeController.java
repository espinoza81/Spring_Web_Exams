package com.example.coffeeshopapp.controller;

import com.example.coffeeshopapp.service.AuthService;
import com.example.coffeeshopapp.service.OrderService;
import com.example.coffeeshopapp.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final AuthService authService;
    private final OrderService orderService;
    private final LoggedUser loggedUser;

    @Autowired
    public HomeController(AuthService authService,
                          OrderService orderService,
                          LoggedUser loggedUser) {
        this.authService = authService;
        this.orderService = orderService;
        this.loggedUser = loggedUser;
    }

    @GetMapping({"/home"})
    public String loggedInIndex(Model model){
        if(!this.authService.isLogged()) {
            return "redirect:/";
        }

//        List<List<OrderDTO>> productsByCategory = this.orderService.getAllProductsByCategory();
//
//        BigDecimal priceOfAllProducts =
//                productsByCategory
//                        .stream()
//                        .map(
//                                list -> list.stream()
//                                        .map(OrderDTO::getPrice)
//                                        .reduce(BigDecimal.ZERO, BigDecimal::add)
//                        ).reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        model.addAttribute("productsByCategory", productsByCategory);
//        model.addAttribute("currentUserId", loggedUser.getId());
//        model.addAttribute("priceOfAllProducts", priceOfAllProducts);


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