package com.likebookapp.controller;

import com.likebookapp.model.dtos.MyPostDTO;
import com.likebookapp.model.dtos.OtherPostDTO;
import com.likebookapp.service.AuthService;
import com.likebookapp.service.PostService;
import com.likebookapp.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final AuthService authService;
    private final PostService postService;
    private final LoggedUser loggedUser;

    @Autowired
    public HomeController(AuthService authService,
                          PostService postService,
                          LoggedUser loggedUser) {
        this.authService = authService;
        this.postService = postService;
        this.loggedUser = loggedUser;
    }

    @GetMapping({"/home"})
    public String loggedInIndex(Model model){
        if(!this.authService.isLogged()) {
            return "redirect:/";
        }

        long loggedUserId = this.authService.getLoggedUserId();

        List<MyPostDTO> myPosts = this.postService.getPostWrittenBy(loggedUserId);
        List<OtherPostDTO> otherPosts = this.postService.getPostNotWrittenBy(loggedUserId);

        model.addAttribute("myPosts", myPosts);
        model.addAttribute("otherPosts", otherPosts);
        model.addAttribute("currentUserId", loggedUserId);

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