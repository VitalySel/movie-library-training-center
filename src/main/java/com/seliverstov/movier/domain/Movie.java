package com.seliverstov.movier.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idmovies")
    private int id;

    @Column(name = "movie_name")
    @NotNull
    private String name;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "description", length = 100000)

    private String description;

    @Column(name = "duration")
    private String duration;

    @Column(name = "budget")
    private String budget;

    @Column(name = "poster")
    private String poster;

    @Column(name = "rating")
    private double rating;

    @ManyToMany
    @JoinTable(name = "movie_genres",
            joinColumns = {@JoinColumn(name = "movie_idmovies", referencedColumnName = "idmovies")},
            inverseJoinColumns = {@JoinColumn(name = "genres_idgenres", referencedColumnName = "idgenres")})
    private List<Genres> genres = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "movie_actors",
            joinColumns = {@JoinColumn(name = "movie_idmovies", referencedColumnName = "idmovies")},
            inverseJoinColumns = {@JoinColumn(name = "actor_idactors", referencedColumnName = "idactors")})
    private Set<Actor> actors = new HashSet<>();

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "movies")
    private Producer producers;

    /*@ManyToMany(mappedBy = "favoriteMovies")
    private List<User> userList = new ArrayList<>();*/

    public Movie() {
    }

    public Movie(String name, String releaseDate, String description, String duration, String budget, Producer producers, String poster) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.description = description;
        this.duration = duration;
        this.budget = budget;
        this.producers = producers;
        this.poster = poster;
    }

    public Movie(String name, String releaseDate, String description, String duration, String budget) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.description = description;
        this.duration = duration;
        this.budget = budget;
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

    public void addGenres(Genres genres) {
        this.genres.add(genres);
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public void addActor(Actor actors) {
        this.actors.add(actors);
    }


    public Producer getProducers() {
        return producers;
    }

    public void setProducers(Producer producers) {
        this.producers = producers;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Movie movie = (Movie) obj;
        return Objects.equals(id,movie.id) &&
                Objects.equals(name, movie.name) &&
                Objects.equals(releaseDate, movie.releaseDate) &&
                Objects.equals(description, movie.description) &&
                Objects.equals(duration, movie.duration) &&
                Objects.equals(budget,movie.budget) &&
                Objects.equals(poster,movie.poster) &&
                Objects.equals(rating,movie.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,name,releaseDate,description,duration,budget,poster,rating);
    }
}
