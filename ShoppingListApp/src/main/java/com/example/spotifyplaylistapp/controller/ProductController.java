package com.example.spotifyplaylistapp.controller;

import com.example.spotifyplaylistapp.model.dtos.AddProductDTO;
import com.example.spotifyplaylistapp.service.AuthService;
import com.example.spotifyplaylistapp.service.ProductService;
import com.example.spotifyplaylistapp.service.UserService;
import com.example.spotifyplaylistapp.util.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ProductController {

    private final ProductService productService;
    private final AuthService authService;

    private final LoggedUser loggedUser;
    private final UserService userService;

    public ProductController(ProductService productService,
                             AuthService authService,
                             LoggedUser loggedUser,
                             UserService userService) {
        this.productService = productService;
        this.authService = authService;
        this.loggedUser = loggedUser;
        this.userService = userService;
    }

    @ModelAttribute("addProductDTO")
    public AddProductDTO initAddProductDTO(){
        return new AddProductDTO();
    }

    @GetMapping("/product/add")
    public String product(){
        if(this.authService.isLogged()) {
            return "product-add";
        }
        return "redirect:/login";
    }

    @PostMapping("/product/add")
    public String addProduct(@Valid AddProductDTO addProductDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        if(!this.authService.isLogged()) {
            return "redirect:/";
        }

        if(bindingResult.hasErrors() || !this.productService.created(addProductDTO)){
            redirectAttributes.addFlashAttribute("addProductDTO", addProductDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.addProductDTO", bindingResult);

            return "redirect:/product/add";
        }

        return "redirect:/home";
    }

    @GetMapping("product/buy/{id}")
    String buyProduct(@PathVariable Long id){
        if(!this.authService.isLogged()) {
            return "redirect:/";
        }

        productService.buyProduct(id);
        return "redirect:/home";

    }

    @GetMapping("/remove_all")
    String clearUserProductList(){
        if(!this.authService.isLogged()) {
            return "redirect:/";
        }

        productService.buyAllProduct(loggedUser.getId());
        return "redirect:/home";

    }
}
