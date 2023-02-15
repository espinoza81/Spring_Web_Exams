package com.example.coffeeshopapp.controller;

import com.example.coffeeshopapp.model.dtos.UserLoginDTO;
import com.example.coffeeshopapp.model.dtos.UserRegistrationDTO;
import com.example.coffeeshopapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ModelAttribute("registrationDTO")
    public UserRegistrationDTO initRegistrationDTO(){
        return new UserRegistrationDTO();
    }

    @ModelAttribute("loginDTO")
    public UserLoginDTO initLoginDTO(){
        return new UserLoginDTO();
    }

    @GetMapping("/register")
    public String register(){
        if(this.authService.isLogged()) {
            return "redirect:/home";
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegistrationDTO registrationDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors() || !this.authService.register(registrationDTO)){
            redirectAttributes.addFlashAttribute("registrationDTO", registrationDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.registrationDTO", bindingResult);

            return "redirect:/register";
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        if(this.authService.isLogged()) {
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/login")
    public String register(@Valid UserLoginDTO loginDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("loginDTO", loginDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.loginDTO", bindingResult);

            return "redirect:/login";
        }

        if(!this.authService.login(loginDTO)){
            redirectAttributes.addFlashAttribute("loginDTO", loginDTO);
            redirectAttributes.addFlashAttribute("badCredentials", true);

            return "redirect:/login";
        }

        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(){
        if(this.authService.isLogged()) {
            this.authService.logout();
        }

        return "redirect:/";
    }
}