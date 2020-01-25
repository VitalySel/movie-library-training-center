package com.seliverstov.movier.repository;

import com.seliverstov.movier.domain.Genres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenresRepository extends JpaRepository<Genres,Long> {

    Genres findById(int id);

    Genres findBygenreName(String name);
}
