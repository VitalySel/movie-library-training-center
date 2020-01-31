package com.seliverstov.movier.controller;

import com.seliverstov.movier.repository.ActorRepository;
import com.seliverstov.movier.repository.GenresRepository;
import com.seliverstov.movier.repository.MovieRepository;
import com.seliverstov.movier.service.ActorService;
import com.seliverstov.movier.service.GenresService;
import com.seliverstov.movier.service.MovieService;
import com.seliverstov.movier.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GenreController {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private GenresRepository genresRepository;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private MovieService movieService;
    @Autowired
    private ActorService actorService;
    @Autowired
    private GenresService genresService;
    @Autowired
    private ProducerService producerService;

    @GetMapping(value = "/movie/genre")
    public String genreFilterMovie(@RequestParam String id, Model model) throws Exception {
        model.addAttribute("moviesGenres", movieService.findMovieByGenre(Integer.parseInt(id)));
        model.addAttribute("genres",genresRepository.findAll());
        return "genreMovie";
    }

    @GetMapping(value = "/actor/genre")
    public String genreFilterActor(@RequestParam String id,Model model) {
        model.addAttribute("actorsGenres",actorService.findActorByGenres(Integer.parseInt(id)));
        model.addAttribute("genres",genresRepository.findAll());
        return "genreActor";
    }

    @GetMapping(value = "/producer/genre")
    public String genreFilterProducer(@RequestParam String id,Model model) {
        model.addAttribute("producersGenres",producerService.findProducerByGenres(Integer.parseInt(id)));
        model.addAttribute("genres",genresRepository.findAll());
        return "genreProducer";
    }
}
