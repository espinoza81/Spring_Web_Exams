package com.likebookapp.controller;

import com.likebookapp.model.dtos.PostCreatedDto;
import com.likebookapp.service.AuthService;
import com.likebookapp.service.PostService;
import com.likebookapp.util.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class PostController {

    private final PostService postService;
    private final AuthService authService;

    private final LoggedUser loggedUser;

    public PostController(PostService postService,
                          AuthService authService,
                          LoggedUser loggedUser) {
        this.postService = postService;
        this.authService = authService;
        this.loggedUser = loggedUser;
    }

    @ModelAttribute("postCreatedDto")
    public PostCreatedDto initPostCreatedDTO(){
        return new PostCreatedDto();
    }

    @GetMapping("/posts")
    public String posts(){
        if(this.authService.isLogged()) {
            return "post-add";
        }
        return "redirect:/login";
    }

    @PostMapping("/posts")
    public String register(@Valid PostCreatedDto postCreatedDto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("postCreatedDto", postCreatedDto);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.postCreatedDto", bindingResult);

            return "redirect:/posts";
        }

        this.postService.created(postCreatedDto);

        return "redirect:/home";
    }

    @GetMapping("posts/like-post/{id}")
    String likePost(@PathVariable Long id){

        postService.likePostWithId(id, loggedUser.getId());
        return "redirect:/home";

    }

    @GetMapping("posts/remove/{id}")
    String removePost(@PathVariable Long id){

        postService.removePostById(id);
        return "redirect:/home";

    }
}
