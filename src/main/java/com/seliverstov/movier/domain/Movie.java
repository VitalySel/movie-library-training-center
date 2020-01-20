package com.seliverstov.movier.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idmovies")
    private Long id;

    @Column(name = "movie_name")
    @NotNull
    private String name;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "description")
    private String description;

    @Column(name = "duration")
    private String duration;

    @Column(name = "budget")
    private String budget;

    @ManyToMany
    @JoinTable(name = "movie_genres",
            joinColumns = {@JoinColumn(name = "movie_idmovies",referencedColumnName = "idmovies")},
            inverseJoinColumns = {@JoinColumn(name = "genres_idgenres",referencedColumnName = "idgenres")})
    private List<Genres> genres = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "movie_actors",
            joinColumns = {@JoinColumn(name = "movie_idmovies",referencedColumnName = "idmovies")},
            inverseJoinColumns = {@JoinColumn(name = "actor_idactors",referencedColumnName = "idactors")})
    private Set<Actor> actors = new HashSet<>();

    @ManyToOne(optional = false,cascade = CascadeType.MERGE)
    @JoinColumn(name = "idmovies",insertable = false, updatable = false)
    private Producer producers;

    public Movie() {
    }

    public Movie(String name, String releaseDate, String description, String duration, String budget, List<Genres> genres, Set<Actor> actors, Producer producers) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.description = description;
        this.duration = duration;
        this.budget = budget;
        this.genres = genres;
        this.actors = actors;
        this.producers = producers;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public Producer getProducers() {
        return producers;
    }

    public void setProducers(Producer producers) {
        this.producers = producers;
    }

    @Override
    public String toString() {
        return name;
    }
}
