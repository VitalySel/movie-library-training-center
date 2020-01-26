package com.seliverstov.movier.controller;

import com.seliverstov.movier.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ActorController {

    @Autowired
    private ActorRepository actorRepository;

    @GetMapping(value = "/actors")
    public String actor(Model model){
        model.addAttribute("actors",actorRepository.findAll());
        return "actors";
    }
}
