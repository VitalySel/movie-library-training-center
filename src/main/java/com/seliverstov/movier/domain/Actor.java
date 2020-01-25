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
    @Column(name = "idactors", unique = true)
    private int id;

    @Column(name = "name", length = 25)
    @NotNull
    private String name;

    @Column(name = "country", length = 25)
    private String country;

    @Column(name = "date_birth", length = 25)
    private String date;

    @Column(name = "photo")
    private String photo;

    @ManyToMany(mappedBy = "actors")
    private Set<Movie> movie = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "actors_genres",
    joinColumns = {@JoinColumn(name = "actor_idactors", referencedColumnName = "idactors")},
    inverseJoinColumns = {@JoinColumn(name = "genre_idgenres",referencedColumnName = "idgenres")})
    private List<Genres> genres = new ArrayList<>();

    /*@ManyToMany(mappedBy = "actors")
    private List<Genres> genres = new ArrayList<>();*/

    public Actor(String name, String country, String date, Set<Movie> movie) {
        this.name = name;
        this.country = country;
        this.date = date;
        this.movie = movie;
    }

    public Actor() {

    }

    public Actor(String name, String country, String date) {
        this.name = name;
        this.country = country;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
