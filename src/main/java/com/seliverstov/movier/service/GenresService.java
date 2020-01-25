package com.seliverstov.movier.service;

import com.seliverstov.movier.domain.Genres;
import com.seliverstov.movier.repository.GenresRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenresService {

    @Autowired
    private GenresRepository genresRepository;

    public Genres getGenresName(String name) throws Exception {
        Genres genres = genresRepository.findBygenreName(name);

        if (genres != null) {
            throw new Exception("Genres already exists with name - " + name);
        }
        return null;
    }

    public void save(Genres genres) {
        genresRepository.save(genres);
    }
}
