package com.seliverstov.movier.repository;

import com.seliverstov.movier.domain.Actor;
import com.seliverstov.movier.domain.Genres;
import com.seliverstov.movier.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    Actor findById(int id);
    Actor findByName(String name);

    List<Actor> findByMovie(Movie movie);

}
