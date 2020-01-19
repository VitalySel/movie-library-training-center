package com.seliverstov.movier.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "actor")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "actor_id", unique = true)
    private Long id;

    @Column(name = "name", length = 25)
    @NotNull
    private String name;

    @Column(name = "surname", length = 25)
    @NotNull
    private String surname;

    @Column(name = "country", length = 25)
    private String country;

    @Column(name = "date_birth", length = 25)
    private String date;

    @ManyToMany(mappedBy = "actors")
    private Set<Movie> movie = new HashSet<>();

    @ManyToMany(mappedBy = "actors")
    private List<Genres> genres = new ArrayList<>();

    public Actor(String name, String surname, String country, String date, Set<Movie> movie, List<Genres> genres) {
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.date = date;
        this.movie = movie;
        this.genres = genres;
    }

    public Actor() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<Movie> getMovie() {
        return movie;
    }

    public void setMovie(Set<Movie> movie) {
        this.movie = movie;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }
}
