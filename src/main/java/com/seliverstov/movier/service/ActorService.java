package com.seliverstov.movier.service;

import com.seliverstov.movier.domain.Actor;
import com.seliverstov.movier.repository.ActorRepository;
import com.seliverstov.movier.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {

    @Autowired
    ActorRepository actorRepo;

    @Autowired
    MovieRepository movieRepo;


    public List<Actor> findByMovieId(int id){
        return actorRepo.findByMovie(movieRepo.findById(id));
    }

    public void save(Actor actor){
        actorRepo.save(actor);
    }
}
