package com.example.coffeeshopapp.controller;

import com.example.coffeeshopapp.model.dtos.AddOrderDTO;
import com.example.coffeeshopapp.service.AuthService;
import com.example.coffeeshopapp.service.OrderService;
import com.example.coffeeshopapp.service.UserService;
import com.example.coffeeshopapp.util.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final AuthService authService;

    private final LoggedUser loggedUser;
    private final UserService userService;

    public OrderController(OrderService orderService,
                           AuthService authService,
                           LoggedUser loggedUser,
                           UserService userService) {
        this.orderService = orderService;
        this.authService = authService;
        this.loggedUser = loggedUser;
        this.userService = userService;
    }

    @ModelAttribute("addOrderDTO")
    public AddOrderDTO initAddProductDTO(){
        return new AddOrderDTO();
    }

    @GetMapping("/order/add")
    public String product(){
        if(this.authService.isLogged()) {
            return "order-add";
        }
        return "redirect:/login";
    }

    @PostMapping("/order/add")
    public String addOrder(@Valid AddOrderDTO addOrderDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        if(!this.authService.isLogged()) {
            return "redirect:/";
        }

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addOrderDTO", addOrderDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.addOrderDTO", bindingResult);

            return "redirect:/order/add";
        }

        this.orderService.created(addOrderDTO);

        return "redirect:/home";
    }

//    @GetMapping("product/buy/{id}")
//    String buyProduct(@PathVariable Long id){
//        if(!this.authService.isLogged()) {
//            return "redirect:/";
//        }
//
//        orderService.buyProduct(id);
//        return "redirect:/home";
//
//    }
//
//    @GetMapping("/remove_all")
//    String clearUserProductList(){
//        if(!this.authService.isLogged()) {
//            return "redirect:/";
//        }
//
//        orderService.buyAllProduct(loggedUser.getId());
//        return "redirect:/home";
//
//    }
}
