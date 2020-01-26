package com.seliverstov.movier.controller;

import com.seliverstov.movier.domain.User;
import com.seliverstov.movier.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping(value = "/movie")
    public String movie(Model model){
        model.addAttribute("movies",movieRepository.findAll());
        return "movie";
    }
}
