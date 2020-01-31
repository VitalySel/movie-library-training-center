package com.seliverstov.movier.controller;

import com.seliverstov.movier.repository.GenresRepository;
import com.seliverstov.movier.repository.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProducerController {

    @Autowired
    private ProducerRepository producerRepository;
    @Autowired
    private GenresRepository genresRepository;

    @GetMapping(value = "/producers")
    public String movie(Model model){
        model.addAttribute("producers",producerRepository.findAll());
        model.addAttribute("genres",genresRepository.findAll());
        return "producers";
    }

    @GetMapping(value = "/producer/search")
    public String searchProducer(@RequestParam(required = false, defaultValue = "") String name, Model model) {

        if (name.isEmpty() && producerRepository.findByName(name) == null) {
            model.addAttribute("message", "Producer not exists");
            model.addAttribute("genres",genresRepository.findAll());
            return "searchProducer";
        }

        model.addAttribute("producers",producerRepository.findByName(name));
        model.addAttribute("genres",genresRepository.findAll());
        return "searchProducer";
    }
}
