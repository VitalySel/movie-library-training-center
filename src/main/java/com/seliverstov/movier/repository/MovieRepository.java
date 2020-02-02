package com.seliverstov.movier.repository;

import com.seliverstov.movier.domain.Genres;
import com.seliverstov.movier.domain.Movie;
import com.seliverstov.movier.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findById(int id);
    Movie findByName(String name);

    List<Movie> findByGenres(int id);

}
