package com.seliverstov.movier.controller;

import com.seliverstov.movier.repository.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProducerController {

    @Autowired
    private ProducerRepository producerRepository;

    @GetMapping(value = "/producers")
    public String movie(Model model){
        model.addAttribute("producers",producerRepository.findAll());
        return "producers";
    }
}
