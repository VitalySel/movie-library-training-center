package com.seliverstov.movier.service;

import com.seliverstov.movier.domain.Item;
import com.seliverstov.movier.domain.Movie;
import com.seliverstov.movier.domain.MovieCart;
import com.seliverstov.movier.domain.User;
import com.seliverstov.movier.repository.ItemRepository;
import com.seliverstov.movier.repository.MovieCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieCartService {

    @Autowired
    private MovieCartRepository movieCartRepository;
    @Autowired
    private ItemRepository itemRepository;

    public void addMovieToMovieCart(User user, Movie movie){
        Optional<MovieCart> movieCart = movieCartRepository.findByUser(user);
        MovieCart cart;
        if (movieCart.isPresent()) {
            cart = movieCart.get();
            Item newItem =cart.find(movie);
            if (newItem == null) {
                Item item =new Item(movie.getId(),user);
                updateList(cart,item);
            }
        }
        else {
            Item item = new Item(movie.getId(),user);
            itemRepository.save(item);
            List<Item> items =new ArrayList<>();
            items.add(item);
            cart = new MovieCart(items, user);
        }

        movieCartRepository.save(cart);
    }

    public void updateList(MovieCart movieCart, Item item){
        List<Item> items =movieCart.getItems();
        items.add(item);
        itemRepository.save(item);
        movieCart.setItems(items);
    }
}
