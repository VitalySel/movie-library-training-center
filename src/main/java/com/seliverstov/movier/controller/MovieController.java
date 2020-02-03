package com.seliverstov.movier.controller;

import com.seliverstov.movier.domain.Genres;
import com.seliverstov.movier.domain.Movie;
import com.seliverstov.movier.domain.User;
import com.seliverstov.movier.repository.GenresRepository;
import com.seliverstov.movier.repository.MovieRepository;
import com.seliverstov.movier.service.MovieService;
import com.sun.corba.se.spi.ior.IdentifiableFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieService movieService;
    @Autowired
    private GenresRepository genresRepository;

    @GetMapping(value = "/movie")
    public String movie(Model model, HttpServletRequest httpServletRequest){

        int page = 0;
        int size = 10;

        if (httpServletRequest.getParameter("page") != null && !httpServletRequest.getParameter("page").isEmpty()) {
            page = Integer.parseInt(httpServletRequest.getParameter("page")) - 1;
        }

        if (httpServletRequest.getParameter("size") != null && !httpServletRequest.getParameter("size").isEmpty()) {
            size = Integer.parseInt(httpServletRequest.getParameter("size"));
        }

        model.addAttribute("movies",movieRepository.findAll(PageRequest.of(page,size)));
        model.addAttribute("genres",genresRepository.findAll(Sort.by(Sort.Direction.ASC,"genreName")));
        return "movie";
    }

    @GetMapping(value = "/movie/search")
    public String movieBySearch(@RequestParam(required = false, defaultValue = "") String name, Model model) {

        if (name.isEmpty()) {
            model.addAttribute("message","String is empty!");
            model.addAttribute("movies",movieRepository.findAll(Sort.by(Sort.Direction.ASC,"name")));
            model.addAttribute("genres",genresRepository.findAll(Sort.by(Sort.Direction.ASC,"genreName")));
            return "search";
        }

        if (movieRepository.findByName(name) == null) {
            model.addAttribute("name",name);
            model.addAttribute("message", "Movie not exists");
            model.addAttribute("movies",movieRepository.findAll(Sort.by(Sort.Direction.ASC,"name")));
            model.addAttribute("genres",genresRepository.findAll(Sort.by(Sort.Direction.ASC,"genreName")));
            return "search";
        }

        model.addAttribute("name",name);
        model.addAttribute("movies",movieRepository.findByName(name));
        model.addAttribute("genres",genresRepository.findAll(Sort.by(Sort.Direction.ASC,"genreName")));
        return "search";
    }

    @GetMapping(value = "/allMovie")
    public String getSortMovie(Model model) {
        model.addAttribute("movies", movieRepository.findAll(Sort.by(Sort.Direction.DESC,"rating")));
        model.addAttribute("genres",genresRepository.findAll(Sort.by(Sort.Direction.ASC,"genreName")));
        return "allMovie";
    }




}
