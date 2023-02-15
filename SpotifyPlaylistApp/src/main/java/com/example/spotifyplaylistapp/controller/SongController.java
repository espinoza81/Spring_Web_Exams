package com.example.spotifyplaylistapp.controller;

import com.example.spotifyplaylistapp.model.dtos.AddSongDTO;
import com.example.spotifyplaylistapp.service.AuthService;
import com.example.spotifyplaylistapp.service.SongService;
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
public class SongController {

    private final SongService songService;
    private final AuthService authService;

    private final LoggedUser loggedUser;
    private final UserService userService;

    public SongController(SongService songService,
                          AuthService authService,
                          LoggedUser loggedUser,
                          UserService userService) {
        this.songService = songService;
        this.authService = authService;
        this.loggedUser = loggedUser;
        this.userService = userService;
    }

    @ModelAttribute("addSongDTO")
    public AddSongDTO initAddSongDTO(){
        return new AddSongDTO();
    }

    @GetMapping("/song/add")
    public String song(){
        if(this.authService.isLogged()) {
            return "song-add";
        }
        return "redirect:/login";
    }

    @PostMapping("/song/add")
    public String register(@Valid AddSongDTO addSongDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addSongDTO", addSongDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.addSongDTO", bindingResult);

            return "redirect:/song/add";
        }

        this.songService.created(addSongDTO);

        return "redirect:/home";
    }

    @GetMapping("song/add_playlist/{id}")
    String likePost(@PathVariable Long id){

        userService.addSongToPlaylist(id, loggedUser.getId());
        return "redirect:/home";

    }

    @GetMapping("/remove_all")
    String clearUserPlaylist(){

        userService.clearUserPlaylist(loggedUser.getId());
        return "redirect:/home";

    }
}
