package com.example.coffeeshopapp.controller;

import com.example.coffeeshopapp.model.dtos.EmployeeWithOrderCountDTO;
import com.example.coffeeshopapp.model.dtos.OrderDTO;
import com.example.coffeeshopapp.service.AuthService;
import com.example.coffeeshopapp.service.OrderService;
import com.example.coffeeshopapp.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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

        List<OrderDTO> ordersPriceInDescOrder = this.orderService.getAllOrdersByPriceDesc();

        int timeToReadyAll =
                ordersPriceInDescOrder
                        .stream()
                        .mapToInt(OrderDTO::getNeededTime)
                        .sum();

        List<EmployeeWithOrderCountDTO> getEmployeeWithOrderCount = this.orderService.employeeWithOrderCount();

        model.addAttribute("ordersPriceInDescOrder", ordersPriceInDescOrder);
        model.addAttribute("getEmployeeWithOrderCount", getEmployeeWithOrderCount);
        model.addAttribute("currentUserId", loggedUser.getId());
        model.addAttribute("timeToReadyAll", timeToReadyAll);


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