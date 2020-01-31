package com.seliverstov.movier.controller;

import com.seliverstov.movier.domain.*;
import com.seliverstov.movier.repository.*;
import com.seliverstov.movier.service.*;
import com.sun.org.apache.regexp.internal.RE;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ProducerRepository producerRepository;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private GenresRepository genresRepository;


    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private ProducerService producerService;
    @Autowired
    private ActorService actorService;
    @Autowired
    private GenresService genresService;


    @Value("${upload.path}")
    private String uploadPath;

    @RequestMapping(value = "/admin")
    public String admin(Map<String,Object> map)
    {
        map.put("userCount",userRepository.count());
        map.put("movieCount", movieRepository.count());
        map.put("actorCount", actorRepository.count());
        map.put("producerCount",producerRepository.count());
        map.put("genresCount",genresRepository.count());
        return "admin";
    }

    @RequestMapping(value = {"/userList"}, method = RequestMethod.GET)
    public String getUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "userList";
    }

    @RequestMapping(value = {"{userid}/userProfileAdmin"}, method = RequestMethod.GET)
    public String getUserProfileAdmin(@PathVariable String userid, Model model) throws NotFoundException {
        User user = userService.getUserId(Long.valueOf(userid));
        model.addAttribute("users", user);
        return "userProfileAdmin";
    }

    @RequestMapping(value = {"{userid}/userEditAdmin"}, method = RequestMethod.GET)
    public String getUserEditAdmin(@PathVariable String userid,Model model) throws NotFoundException {
        User user = userService.getUserId(Long.valueOf(userid));
        model.addAttribute("users",user);
        return "userEditAdmin";
    }

    @RequestMapping(value = "userEdit" ,method = RequestMethod.POST)
    public String getUserEditAdmin(@RequestParam String username, String realname,String mail, @RequestParam String userId) throws NotFoundException {
        User usr = userService.getUserId(Long.valueOf(userId));
        usr.setUsername(username);
        usr.setRealname(realname);
        usr.setMail(mail);
        userService.update(usr);
        return  "redirect:"+userId+"/userProfileAdmin";
    }

    @RequestMapping(value = {"/userMakeAdmin"},method = RequestMethod.GET)
    public String userMakeAdmin(Model model) throws NotFoundException {
        List<User> users =new ArrayList<>(userRepository.findAll());
        List<User> userNotAdmin =new ArrayList<>();
        for (User user: users) {
            Set userRoles = user.getRoles();
            if (!userRoles.contains(Role.ADMIN)){
                userNotAdmin.add(user);
            }
        }
        model.addAttribute("users", userNotAdmin);
        return "userMakeAdmin";
    }

    @RequestMapping(value = {"/makeAdmin"},method = RequestMethod.POST)
    public String makeAdmin(@RequestParam String userid, @RequestParam Map<String,String> form) throws NotFoundException {
        User user = userService.getUserId(Long.valueOf(userid));
        user.getRoles().add(Role.ADMIN);

        userService.update(user);
        return "redirect:/userList";
    }

    @RequestMapping(value = {"/movieAdmin"},method = RequestMethod.GET)
    public String movieAdmin(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "movieAdmin";
    }

    @RequestMapping(value = {"/addMovieAdmin"},method = RequestMethod.GET)
    public String addMovieAdmin(Model model) {
        model.addAttribute("producers", producerRepository.findAll());
        return "addMovieAdmin";
    }

    @RequestMapping(value = {"/addMovie"},method = RequestMethod.POST)
    public String addMovieAdminForm(@RequestParam String name, @RequestParam String releaseDate,
                                    @RequestParam String description, @RequestParam String duration, @RequestParam String budget,@RequestParam String producerName) {
        Movie movie = new Movie(name,releaseDate,description,duration,budget,producerRepository.findByName(producerName), "null");
        movieRepository.save(movie);
        return "redirect:/movieAdmin";
    }


    @RequestMapping(value = {"/producerAdmin"}, method = RequestMethod.GET)
    public String producerAdmin(Model model) {
        model.addAttribute("producers", producerRepository.findAll());
        return "producerAdmin";
    }

    @RequestMapping(value = {"/addProducerAdmin"}, method = RequestMethod.GET)
    public String addProducerAdmin(Model model) {
        return "addProducerAdmin";
    }

    @RequestMapping(value = {"/addProducer"}, method = RequestMethod.POST)
    public String addProducerAdminForm(@RequestParam String name, @RequestParam String country, @RequestParam String date) {
        Producer producer = new Producer(name,country,date);
        producerRepository.save(producer);
        return "redirect:/producerAdmin";
    }


    @RequestMapping(value = {"/movierInfoAdmin/{movieid}"}, method = RequestMethod.GET)
    public String getMovieInfoAdmin(@PathVariable String movieid, Model model) throws NotFoundException {
        Movie movie = movieService.getMovieId(Integer.parseInt(movieid));
        model.addAttribute("movies",movie);
        return "movierInfoAdmin";
    }

    @RequestMapping(value = {"{movieid}/movierEditAdmin"}, method = RequestMethod.GET)
    public String getMovieEditAdmin(@PathVariable String movieid, Model model) throws NotFoundException {
        Movie movie = movieService.getMovieId(Integer.parseInt(movieid));
        model.addAttribute("movies",movie);
        return "movierEditAdmin";
    }

    @RequestMapping(value = "movieEdit" ,method = RequestMethod.POST)
    public String getMovieEditAdminForm(@RequestParam String name, String releaseDate,String description, String duration, String budget, @RequestParam String movieId) throws NotFoundException {
        Movie movie = movieService.getMovieId(Integer.parseInt(movieId));
        movie.setName(name);
        movie.setReleaseDate(releaseDate);
        movie.setDescription(description);
        movie.setDuration(duration);
        movie.setBudget(budget);
        movieService.update(movie);
        return  "redirect:"+movieId+"/movierInfoAdmin";
    }

    @RequestMapping(value = {"/producerInfoAdmin/{producerid}"}, method = RequestMethod.GET)
    public String getProducerInfoAdmin(@PathVariable String producerid, Model model) throws NotFoundException {
        Producer producer = producerService.getProducerId(Integer.parseInt(producerid));
        model.addAttribute("producers", producer);
        return "producerInfoAdmin";
    }

    @RequestMapping(value = {"{producerid}/producerEditAdmin"}, method = RequestMethod.GET)
    public String getProducerEditAdmin(@PathVariable String producerid, Model model) throws NotFoundException {
        Producer producer = producerService.getProducerId(Integer.parseInt(producerid));
        model.addAttribute("producers",producer);
        return "producerEditAdmin";
    }

    @RequestMapping(value = "producerEdit", method = RequestMethod.POST)
    public String getProducerEditAdminForm(@RequestParam String name, String country, String date, @RequestParam String producerId) throws NotFoundException {
        Producer producer = producerService.getProducerId(Integer.parseInt(producerId));
        producer.setName(name);
        producer.setCountry(country);
        producer.setDate(date);
        producerService.update(producer);
        return "redirect:" + producerId + "/producerInfoAdmin";
    }

    @RequestMapping(value = "/actorAdmin",method = RequestMethod.GET)
    public String actorAdmin(Model model){
        model.addAttribute("actors", actorRepository.findAll());
        return "actorAdmin";
    }

    @RequestMapping(value = "/actorInfoAdmin/{actorid}",method = RequestMethod.GET)
    public String getActorInfoAdmin(@PathVariable String actorid, Model model) throws NotFoundException {
        Actor actor = actorService.getActorId(Integer.parseInt(actorid));
        model.addAttribute("actors", actor);
        return "actorInfoAdmin";
    }

    @RequestMapping(value = "addActorAdmin", method = RequestMethod.GET)
    public String addActorAdmin() {
        return "addActorAdmin";
    }

    @RequestMapping(value = {"/addActor"}, method = RequestMethod.POST)
    public String addActorAdminForm(@RequestParam String name, @RequestParam String country, @RequestParam String date) {
        Actor actor = new Actor(name,country,date);
        actorRepository.save(actor);
        return "redirect:/actorAdmin";
    }

    @RequestMapping(value = {"{actorid}/actorEditAdmin"}, method = RequestMethod.GET)
    public String getActorEditAdmin(@PathVariable String actorid, Model model) throws NotFoundException {
        Actor actor = actorService.getActorId(Integer.parseInt(actorid));
        model.addAttribute("actors",actor);
        return "actorEditAdmin";
    }

    @RequestMapping(value = "actorEdit", method = RequestMethod.POST)
    public String getActorEditAdminForm(@RequestParam String name, String country, String date, @RequestParam String actorId) throws NotFoundException {
        Actor actor = actorService.getActorId(Integer.parseInt(actorId));
        actor.setName(name);
        actor.setCountry(country);
        actor.setDate(date);
        actorService.save(actor);
        return "redirect:" + actorId + "/actorInfoAdmin";
    }

    @RequestMapping(value = {"/addMovieActors/{movieid}"}, method = RequestMethod.GET)
    public String addMovieActor(@PathVariable String movieid, Model model) throws NotFoundException {
        Movie movie = movieService.getMovieId(Integer.parseInt(movieid));
        model.addAttribute("movies",movie);
        model.addAttribute("actors", actorRepository.findAll());
        return "addMovieActors";
    }

    @RequestMapping(value = {"/addMovieActors/{movieid}/{actorid}"}, method = RequestMethod.GET)
    public String addMovieActorForm(@PathVariable("movieid") String movieid, @PathVariable("actorid") String actorid) throws NotFoundException {
        Movie movie = movieService.getMovieId(Integer.parseInt(movieid));

        movie.addActor(actorService.getActorId(Integer.parseInt(actorid)));
        movieService.update(movie);
        return "redirect:/addMovieActors/" + movieid;
    }

    @RequestMapping(value = "/genresAdmin",method = RequestMethod.GET)
    public String genresAdmin(Model model){
        model.addAttribute("genres", genresRepository.findAll());
        return "genresAdmin";
    }

    @RequestMapping(value = "/addGenresAdmin",method = RequestMethod.GET)
    public String addGenresAdmin(Model model){
        return "addGenresAdmin";
    }

    @RequestMapping(value = "/addGenre",method = RequestMethod.POST)
    public String addGenresAdminForm(@RequestParam String name, Map<String,Object> map) throws Exception {
        Genres genreFromDb = genresService.getGenresName(name);

        if (genreFromDb != null) {
            map.put("message","Genres already exists");
            return "addGenresAdmin";
        }

        Genres genres = new Genres(name);
        genresService.save(genres);

        return "redirect:/genresAdmin";
    }

    @RequestMapping(value = {"/genresInfoAdmin/{genreid}"},method = RequestMethod.GET)
    public String genresInfoAdmin(@PathVariable String genreid, Model model) throws Exception {
        Genres genres = genresService.getGenresId(Integer.parseInt(genreid));
        model.addAttribute("genres", genres);
        return "genresInfoAdmin";
    }

    @RequestMapping(value = {"{genreid}/genresEditAdmin"},method = RequestMethod.GET)
    public String genresEditAdmin(@PathVariable String genreid, Model model) throws Exception {
        Genres genres = genresService.getGenresId(Integer.parseInt(genreid));
        model.addAttribute("genres", genres);
        return "genresEditAdmin";
    }

    @RequestMapping(value = "genreEdit", method = RequestMethod.POST)
    public String getGenresEditAdminForm(@RequestParam String name, @RequestParam String genreId, Map<String, Object> map) throws Exception {
        Genres genresFromDb = genresService.getGenresName(name);
        if (genresFromDb != null) {
            map.put("message","Genres already exists");
            return "genresEditAdmin";
        }

        Genres genres = genresService.getGenresId(Integer.parseInt(genreId));
        genres.setGenreName(name);
        genresService.save(genres);

        return "redirect:" + genreId + "/genresInfoAdmin";
    }

    @RequestMapping(value = {"/addMovieGenres/{movieid}"}, method = RequestMethod.GET)
    public String addMovieGenres(@PathVariable String movieid, Model model) throws NotFoundException {
        Movie movie = movieService.getMovieId(Integer.parseInt(movieid));
        model.addAttribute("movies",movie);
        model.addAttribute("genres",genresRepository.findAll());
        return "addMovieGenres";
    }

    @RequestMapping(value = {"/addMovieGenres/{movieid}/{genreid}"}, method = RequestMethod.GET)
    public String addMovieGenreForm(@PathVariable("movieid") String movieId, @PathVariable("genreid") String genreid) throws Exception {
        Movie movie = movieService.getMovieId(Integer.parseInt(movieId));
        List<Genres> genresList = movie.getGenres();

        if (genresList.contains(genresRepository.findById(Integer.parseInt(genreid)))){
            return "redirect:/addMovieGenres/" + movieId;
        }

        movie.addGenres(genresService.getGenresId(Integer.parseInt(genreid)));
        movieService.update(movie);
        return "redirect:/addMovieGenres/" + movieId;
    }

    @RequestMapping(value = {"{actorid}/addActorGenres"}, method = RequestMethod.GET)
    public String addActorGenres(@PathVariable String actorid, Model model) throws NotFoundException {
        Actor actor = actorService.getActorId(Integer.parseInt(actorid));
        model.addAttribute("actors",actor);
        return "addActorGenres";
    }

    @RequestMapping(value = {"addActorGenre"}, method = RequestMethod.POST)
    public String addActorGenreForm(@RequestParam String actorId, @RequestParam String genreName) throws NotFoundException {
        List<String> genreNameInput = Arrays.asList(genreName.split(","));
        Actor actor = actorService.getActorId(Integer.parseInt(actorId));
        actor.setGenres(genresService.findGenreName(genreNameInput));
        actorService.save(actor);
        return "redirect:" + actorId + "/actorInfoAdmin";
    }

    @RequestMapping(value = {"{producerid}/addProducerGenres"}, method = RequestMethod.GET)
    public String addProducerGenres(@PathVariable String producerid, Model model) throws NotFoundException {
        Producer producer = producerService.getProducerId(Integer.parseInt(producerid));
        model.addAttribute("producers",producer);
        return "addProducerGenres";
    }


    @RequestMapping(value = {"addProducerGenre"}, method = RequestMethod.POST)
    public String addProducerGenreForm(@RequestParam String producerId, @RequestParam String genreName) throws NotFoundException {
        List<String> genreNameInput = Arrays.asList(genreName.split(","));
        Producer producer = producerService.getProducerId(Integer.parseInt(producerId));
        producer.setGenres(genresService.findGenreName(genreNameInput));
        producerService.update(producer);
        return "redirect:" + producerId + "/producerInfoAdmin";
    }

    @RequestMapping(value = "/dataParsing",method = RequestMethod.GET)
    public String parsing(Model model){
        return "dataParsing";
    }

    @RequestMapping(value = {"/dataParsing"}, method = RequestMethod.POST)
    public String parsing(@RequestParam String start, Map<String, Object> model) {
        ParserService parserService = new ParserService();
        for (int i = Integer.parseInt(start); i < Integer.parseInt(start)+1; i++) {
            try {
                String[] res = parserService.parsePage(i).split("\\|");
                if(!res[0].equals("null") && movieRepository.findByName(res[0]) == null) {
                    if (producerRepository.findByName(res[9]) == null) {
                        String[] prod = parserService.parsDirector(res[10]).split("\\|");
                        Producer producer;

                        if (prod.length > 3) {
                            producer = new Producer(prod[0], prod[2], prod[1], prod[3]);
                            producerRepository.save(producer);
                        }
                        else {
                            producer = new Producer(prod[0], prod[2], prod[1]);
                            producerRepository.save(producer);
                        }
                    }

                    Movie movie = new Movie(res[0], res[4], res[7], res[2], res[3], producerRepository.findByName(res[9]), res[5]);

                    String[] actors = res[8].split(",");
                    for (int j = 0; j < (actors.length > 3 ? 3 : actors.length); j++) {
                        Actor actor;
                        String[] act = parserService.parsDirector(actors[j]).split("\\|");

                        if(actorRepository.findByName(act[0]) != null){
                            actor = actorRepository.findByName(act[0]);
                        }
                        else{

                            if (act.length > 3) {
                                actor = new Actor(act[0], act[2], act[1], act[3]);
                            }
                            else actor = new Actor(act[0], act[2], act[1]);
                        }

                        String[] genres = res[6].split(",");
                        for (int k = 0; k < (genres.length > 1 ? 1 : genres.length); k++) {
                            Genres genres1;
                            if(genresRepository.findBygenreName(genres[k]) != null) {
                                genres1 = genresRepository.findBygenreName(genres[k]);
                            }
                            else{
                                genres1 = new Genres(genres[k]);
                                genresRepository.save(genres1);
                            }
                            if(!actor.getGenres().contains(genres1)) {
                                actor.addGenres(genres1);
                            }
                            if(!movie.getGenres().contains(genres1)){
                                movie.addGenres(genres1);
                            }
                            if(!producerRepository.findByName(res[9]).getGenres().contains(genres1)){
                                Producer producer = producerRepository.findByName(res[9]);
                                producer.addGenres(genres1);
                                producer.addMovies(movie);
                                producerRepository.save(producer);
                            }
                        }
                        actor.addMovie(movie);
                        actorRepository.save(actor);
                        movie.addActor(actor);
                    }
                    movieRepository.save(movie);
                }
                else {
                    model.put("message","Incorrect movie or movie exists. Try another one");
                    return "dataParsing";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/movieAdmin";
    }

}
