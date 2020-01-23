package com.seliverstov.movier.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "producer")
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idproducers", unique = true)
    private int id;

    @Column(name = "producer_name")
    @NotNull
    private String name;

    @Column(name = "producer_country")
    private String country;

    @Column(name = "producer_date")
    private String date;

    @OneToMany(mappedBy = "producers")
    private Set<Movie> movies = new HashSet<>();

    @ManyToMany(mappedBy = "producers")
    private List<Genres> genres = new ArrayList<>();

    public Producer(){
    }

    public Producer(String name, String country, String date) {
        this.name = name;
        this.country = country;
        this.date = date;
    }

    public int getIdProducers() {
        return id;
    }

    public void setIdProducers(int id) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }
}
