package com.seliverstov.movier.controller;

import com.seliverstov.movier.domain.Movie;
import com.seliverstov.movier.domain.User;
import com.seliverstov.movier.repository.ActorRepository;
import com.seliverstov.movier.repository.MovieRepository;
import com.seliverstov.movier.repository.ProducerRepository;
import com.seliverstov.movier.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private ProducerRepository producerRepository;
    @Autowired
    private UserRepository userRepository;


    @RequestMapping(value="/",method = RequestMethod.GET)
    public String main(Map<String, Object> model, @AuthenticationPrincipal User user) {
        model.put("movieCount",movieRepository.count());
        model.put("actorCount",actorRepository.count());
        model.put("producerCount",producerRepository.count());
        return "index";
    }
}
