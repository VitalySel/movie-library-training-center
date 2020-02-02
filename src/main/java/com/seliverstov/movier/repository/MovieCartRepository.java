package com.seliverstov.movier.repository;

import com.seliverstov.movier.domain.MovieCart;
import com.seliverstov.movier.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieCartRepository extends JpaRepository<MovieCart, Integer> {
    Optional<MovieCart> findByUser(User user);
}
