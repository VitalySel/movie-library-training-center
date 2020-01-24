package com.seliverstov.movier.service;

import com.seliverstov.movier.domain.Movie;
import com.seliverstov.movier.repository.MovieRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie getMovieId(int id) throws NotFoundException {

        Optional<Movie> optionalMovie = Optional.ofNullable(movieRepository.findById(id));

        if (!optionalMovie.isPresent()) {
            throw new NotFoundException("Movie not found with id - " + id);
        }
        return optionalMovie.get();
    }

    public void update(Movie movie) {
        movieRepository.save(movie);
    }
}
