package com.seliverstov.movier.service;

import com.seliverstov.movier.domain.Movie;
import com.seliverstov.movier.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;


}
