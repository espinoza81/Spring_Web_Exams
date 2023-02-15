package com.example.spotifyplaylistapp.controller;

import com.example.spotifyplaylistapp.model.dtos.SongDTO;
import com.example.spotifyplaylistapp.model.entity.StyleType;
import com.example.spotifyplaylistapp.service.AuthService;
import com.example.spotifyplaylistapp.service.SongService;
import com.example.spotifyplaylistapp.util.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final AuthService authService;
    private final SongService songService;
    private final LoggedUser loggedUser;

    @Autowired
    public HomeController(AuthService authService,
                          SongService songService,
                          LoggedUser loggedUser) {
        this.authService = authService;
        this.songService = songService;
        this.loggedUser = loggedUser;
    }

    @GetMapping({"/home"})
    public String loggedInIndex(Model model){
        if(!this.authService.isLogged()) {
            return "redirect:/";
        }

        List<SongDTO> pop = this.songService.getStyleSongs(StyleType.POP);
        List<SongDTO> rock = this.songService.getStyleSongs(StyleType.ROCK);
        List<SongDTO> jazz = this.songService.getStyleSongs(StyleType.JAZZ);
        List<SongDTO> userPlaylist = this.songService.getUserLoggedPlaylist(loggedUser.getId());

        int duration = userPlaylist
                .stream()
                .mapToInt(SongDTO::getDuration)
                .sum();

        int min = duration / 60;
        int sec = duration % 60;

        model.addAttribute("popSong", pop);
        model.addAttribute("rockSong", rock);
        model.addAttribute("jazzSong", jazz);
        model.addAttribute("userPlaylist", userPlaylist);
        model.addAttribute("currentUserId", loggedUser.getId());
        model.addAttribute("min", min);
        model.addAttribute("sec", sec);

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