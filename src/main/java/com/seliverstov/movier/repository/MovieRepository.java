package com.seliverstov.movier.repository;

import com.seliverstov.movier.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findById(int id);
    Movie findByName(String name);
}
