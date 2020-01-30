package com.seliverstov.movier.controller;

import com.seliverstov.movier.repository.GenresRepository;
import com.seliverstov.movier.repository.MovieRepository;
import com.seliverstov.movier.service.MovieService;
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
    private MovieService movieService;

    @GetMapping(value = "/movie/genre")
    public String genreFilterMovie(@RequestParam String id, Model model) throws Exception {
        model.addAttribute("moviesGenres", movieService.findMovieByGenre(Integer.parseInt(id)));
        model.addAttribute("genres",genresRepository.findAll());
        return "genreMovie";
    }
}
