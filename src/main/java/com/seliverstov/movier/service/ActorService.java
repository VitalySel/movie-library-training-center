package com.seliverstov.movier.service;

import com.seliverstov.movier.domain.Actor;
import com.seliverstov.movier.domain.Movie;
import com.seliverstov.movier.repository.ActorRepository;
import com.seliverstov.movier.repository.GenresRepository;
import com.seliverstov.movier.repository.MovieRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepo;

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private GenresRepository genresRepository;


    public List<Actor> findByMovieId(int id){
        return actorRepo.findByMovie(movieRepo.findById(id));
    }

    public Actor getActorId(int id) throws NotFoundException {
        Optional<Actor> actorOptional = Optional.ofNullable(actorRepo.findById(id));
        if (!actorOptional.isPresent()) {
            throw new NotFoundException("Actor not found with id " + id);
        }
        return actorOptional.get();
    }

    public Set<Actor> findActorName(List<String> actorList) throws NotFoundException {
        List<Actor> actors = new ArrayList<>();

        for (String actorName:actorList) {
            Actor actor = actorRepo.findByName(actorName);
            if (actor == null ) throw new NotFoundException("Actor not found with name - " + actorName);
            else actors.add(actor);
        }
        Set<Actor> actorSet = new HashSet<>(actors);
        return actorSet;
    }

    public void save(Actor actor){
        actorRepo.save(actor);
    }

    public List<Actor> findActorByGenres(int id) {
        List<Actor> actors = new ArrayList<>(genresRepository.findById(id).getActors());
        return actors;
    }
}
