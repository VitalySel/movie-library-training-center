package com.seliverstov.movier.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "movie_cart")
public class MovieCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne
    private User user;

    @ElementCollection(targetClass = Item.class, fetch = FetchType.EAGER)
    @ManyToMany
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private List<Item> items;

    public MovieCart() {
    }

    public MovieCart(List<Item> items, User user) {
        this.items = items;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Item find(Movie movie) {
        for (int i = 0; i < items.size(); i++ ) {
            if (items.get(i).getMovieId() == movie.getId()){
                return items.get(i);
            }
        }
        return null;
    }
}
