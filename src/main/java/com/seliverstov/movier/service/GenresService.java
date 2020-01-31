package com.seliverstov.movier.service;

import com.seliverstov.movier.domain.Actor;
import com.seliverstov.movier.domain.Genres;
import com.seliverstov.movier.repository.ActorRepository;
import com.seliverstov.movier.repository.GenresRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GenresService {

    @Autowired
    private GenresRepository genresRepository;
    @Autowired
    private ActorRepository actorRepository;

    public Genres getGenresName(String name) throws Exception {
        Genres genres = genresRepository.findBygenreName(name);

        if (genres != null) {
            throw new Exception("Genres already exists with name - " + name);
        }
        return null;
    }

    public Genres getGenresId(int id) throws Exception {

        Optional<Genres> optionalGenres = Optional.ofNullable(genresRepository.findById(id));

        if (!optionalGenres.isPresent()){
            throw new Exception("Genres not found with id - " + id);
        }
        return optionalGenres.get();
    }

    public List<Genres> findGenreName(List<String> genreList) throws NotFoundException {
        List<Genres> genres = new ArrayList<>();

        for (String genreName:genreList) {
            Genres genre = genresRepository.findBygenreName(genreName);
            if (genre == null ) throw new NotFoundException("Genre not found with name - " + genreName);
            else genres.add(genre);
        }

        return genres;
    }

    public void save(Genres genres) {
        genresRepository.save(genres);
    }

   /* public Set<Genres> GenresDistinctByActors(){

        List<Actor> actorsList = actorRepository.findAll();
        Set<Genres> genresSet = new HashSet<>();
        for (Actor actor: actorsList) {
            genresSet.addAll(actor.getGenres());
        }
        return genresSet;
    }*/
}
