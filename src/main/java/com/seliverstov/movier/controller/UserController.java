package com.seliverstov.movier.controller;

import com.seliverstov.movier.domain.Item;
import com.seliverstov.movier.domain.Movie;
import com.seliverstov.movier.domain.MovieCart;
import com.seliverstov.movier.domain.User;
import com.seliverstov.movier.repository.ItemRepository;
import com.seliverstov.movier.repository.MovieCartRepository;
import com.seliverstov.movier.repository.MovieRepository;
import com.seliverstov.movier.repository.UserRepository;
import com.seliverstov.movier.service.MovieCartService;
import com.seliverstov.movier.service.MovieService;
import com.seliverstov.movier.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieCartRepository movieCartRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieCartService movieCartService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping(value = "/profile")
    public String profile(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("users",user);
        return "profile";
    }

    @GetMapping(value = "/profile/edit")
    public String editProfile(Model model,@AuthenticationPrincipal User user){
        model.addAttribute("users",user);
        return "profileEdit";
    }
    @PostMapping(value = "/profile/edit")
    public String editProfile(@RequestParam String username,String realname,String mail, @AuthenticationPrincipal User user) {
        User usr = user;
        usr.setRealname(realname);
        usr.setUsername(username);
        usr.setMail(mail);
        userService.update(usr);
        return "redirect:/profile";
    }

    @GetMapping(value = "/addAvatar")
    public String addAvatar() {
        return "addAvatar";
    }

    @PostMapping(value = "/addAvatar")
    public String addAvatarUser(@RequestParam ("file") MultipartFile file, @AuthenticationPrincipal User user) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename =uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            User usr = user;
            usr.setAvatar(resultFilename);
            userService.update(usr);
        }
        return "redirect:/profile";
    }

    @GetMapping(value = "addMovieUsrList")
    public String addFavoriteMovieUsr(@RequestParam String movieid, @AuthenticationPrincipal User user) {

        if (userRepository.findByUsername(user.getUsername()) == null) {
            return "redirect:/movie";
        }

        movieCartService.addMovieToMovieCart(user,movieRepository.findById(Integer.parseInt(movieid)));
        return "redirect:/movie";
    }

    @PostMapping(value = "/deleteMovieUsrList")
    public String deleteFavoriteMovieUsr(@RequestParam String movieid, @AuthenticationPrincipal User user)
    {
        if (userRepository.findByUsername(user.getUsername()) == null) {
            return "redirect:/movie";
        }

        Movie movie = movieRepository.findById(Integer.parseInt(movieid));
        MovieCart movieCart = movieCartRepository.findByUser(user).get();

        Item newItem = movieCart.find(movie);
        itemRepository.deleteById(newItem.getId());

        List<Item> items = movieCart.getItems();

        movieCart.getItems().removeIf(item -> item.getId().equals(newItem.getId()));

        movieCart.setItems(items);
        movieCartRepository.save(movieCart);

        return "redirect:/profile/favoriteMovie";
    }

    @GetMapping(value = "/profile/favoriteMovie")
    public String getFavoriteMovie(@AuthenticationPrincipal User user, Model model) {

        if (userRepository.findByUsername(user.getUsername()) == null) {
            return "redirect:/";
        }

        Optional<MovieCart> cart = movieCartRepository.findByUser(user);
        List<Item> items = cart.get().getItems();

        if (!cart.isPresent() || items.size() == 0) {
            model.addAttribute("users",user);
            model.addAttribute("message","Favorite list is empty");
            return "/favoriteMovie";
        }
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            Movie movie = movieRepository.findById(items.get(i).getMovieId());
            movies.add(movie);
        }

        model.addAttribute("users", user);
        model.addAttribute("movies", movies);
        return "/favoriteMovie";
    }

    @PostMapping(value = "/sendMovieUsrList")
    public String sendMailMovie(@AuthenticationPrincipal User user, Model model) {

        if (userRepository.findByUsername(user.getUsername()) == null || !movieCartRepository.findByUser(user).isPresent()) {
            return "/favoriteMovie";
        }
        User usr = user;
        Optional<MovieCart> movieCart = movieCartRepository.findByUser(user);
        List<Item> items = movieCart.get().getItems();

        if (items.size() == 0){
            model.addAttribute("message","Favorite list is empty");
            return "redirect:/profile/favoriteMovie";
        }

        userService.sendMailMovies(usr);
        return "redirect:/profile/favoriteMovie";
    }
}
