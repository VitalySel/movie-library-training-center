package com.seliverstov.movier.service;

import com.seliverstov.movier.domain.Actor;
import com.seliverstov.movier.repository.ActorRepository;
import com.seliverstov.movier.repository.MovieRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {

    @Autowired
    ActorRepository actorRepo;

    @Autowired
    MovieRepository movieRepo;


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

    public void save(Actor actor){
        actorRepo.save(actor);
    }
}
