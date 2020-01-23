package com.seliverstov.movier.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "genres")
public class Genres {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idgenres", unique = true)
    private int id;

    @Column(name = "genre_name",unique = true, length = 25)
    @NotNull
    private String genreName;

    @ManyToMany(mappedBy = "genres")
    private Set<Movie> movies = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "genres_actors",
            joinColumns = {@JoinColumn(name = "genre_idgenres",referencedColumnName = "idgenres")},
            inverseJoinColumns = {@JoinColumn(name = "actor_idactors",referencedColumnName = "idactors")})
    private Set<Actor> actors = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "genres_producers",
            joinColumns = {@JoinColumn(name = "genre_idgenres",referencedColumnName = "idgenres")},
            inverseJoinColumns = {@JoinColumn(name = "producer_idproducers",referencedColumnName = "idproducers")})
    private Set<Producer> producers = new HashSet<>();

    public Genres() {
    }

    public Genres(String genreName, Set<Movie> movies, Set<Actor> actors) {
        this.genreName = genreName;
        this.movies = movies;
        this.actors = actors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    @Override
    public String toString() {
        return genreName;
    }
}
