package com.seliverstov.movier.service;

import com.seliverstov.movier.domain.*;
import com.seliverstov.movier.repository.MovieCartRepository;
import com.seliverstov.movier.repository.MovieRepository;
import com.seliverstov.movier.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private MovieCartRepository movieCartRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User findByActivationCode(String code){
        return userRepo.findByActivationCode(code);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user with name '" + username + "'");
        }
        return user;
    }

    public User getUserId(Long id) throws NotFoundException {

        Optional<User> optionalUser = userRepo.findById(id);

        if (!optionalUser.isPresent()) {
            throw new NotFoundException("User not found with id - " + id);
        }

        return optionalUser.get();
    }

    public void addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        String message = String.format(
                "Hello %s! \n" +
                        "Welcome to MovieCatalog. Visit next link to activate your account : \n" +
                        "http://localhost:8080/activate/%s",
                user.getRealname(),
                user.getActivationCode()
        );
        mailService.send(user.getMail(),"Activation code", message);
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);
        if(user == null){
            return false;
        }
        user.setActivationCode(null);
        user.setActive(true);
        userRepo.save(user);
        return true;
    }

    public void update(User user){
        userRepo.save(user);
    }

    public void sendMailMovies(User user) {

        userRepo.findByUsername(user.getUsername());

        StringBuilder movieSend = new StringBuilder();
        String thanks = "\n \n" +
                "Thank you for your list movie in our movier, waiting for moviegoers again!!!";

        Optional<MovieCart> cart = movieCartRepository.findByUser(user);
        List<Item> items = cart.get().getItems();
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            Movie movie = movieRepository.findById(items.get(i).getMovieId());
            movies.add(movie);
        }
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            movieSend.append("Film name: ")
                    .append(movie.getName())
                    .append("  \n")
                    .append("Duration: ")
                    .append(movie.getDuration())
                    .append("  \n")
                    .append("Rating: ")
                    .append(movie.getRating())
                    .append("  \n")
                    .append("Producer: ")
                    .append(movie.getProducers().getName())
                    .append("  \n \n");
        }
        StringBuilder message = new StringBuilder();
        message.append("Aloha, ")
                .append(user.getUsername())
                .append(" ! \n \n")
                .append("Your favorite movies from our site")
                .append("  \n \n")
                .append(movieSend)
                .append(thanks);

        mailService.send(user.getMail(),"Favorite Movie",message.toString());
    }

}