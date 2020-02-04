package com.seliverstov.movier.controller;

import com.seliverstov.movier.domain.*;
import com.seliverstov.movier.repository.*;
import com.seliverstov.movier.service.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.*;

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
    @Autowired
    private SqlService sqlService;


    @Value("${upload.path}")
    private String uploadPath;

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/admin")
    public String admin(@AuthenticationPrincipal User user, Map<String,Object> map)
    {

        map.put("userCount",userRepository.count());
        map.put("movieCount", movieRepository.count());
        map.put("actorCount", actorRepository.count());
        map.put("producerCount",producerRepository.count());
        map.put("genresCount",genresRepository.count());
        return "admin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"/userList"}, method = RequestMethod.GET)
    public String getUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"{userid}/userProfileAdmin"}, method = RequestMethod.GET)
    public String getUserProfileAdmin(@PathVariable String userid, Model model) throws NotFoundException {
        User user = userService.getUserId(Long.valueOf(userid));
        model.addAttribute("users", user);
        return "userProfileAdmin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"{userid}/userEditAdmin"}, method = RequestMethod.GET)
    public String getUserEditAdmin(@PathVariable String userid,Model model) throws NotFoundException {
        User user = userService.getUserId(Long.valueOf(userid));
        model.addAttribute("users",user);
        return "userEditAdmin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "userEdit" ,method = RequestMethod.POST)
    public String getUserEditAdmin(@RequestParam String username, String realname,String mail, @RequestParam String userId,Model model) throws NotFoundException {
        User usr = userService.getUserId(Long.valueOf(userId));

        if (username.isEmpty() || realname.isEmpty() || mail.isEmpty() || username.equals(" ") || realname.equals(" ") || mail.equals(" ")) {
            model.addAttribute("message", "Incorrect information!");
            model.addAttribute("users", usr);
            return "redirect:" +userId +"/userEditAdmin";
        }

        if (!usr.getUsername().equals(username)){
            if (userRepository.findByUsername(username) != null){
                model.addAttribute("message", "Username already exists!");
                model.addAttribute("users", usr);
                return "redirect:" +userId +"/userEditAdmin";
            }
        }
        if (!usr.getMail().equals(mail)) {
            if (userRepository.findByMail(mail) != null) {
                model.addAttribute("message", "Mail already exists!");
                model.addAttribute("users", usr);
                return "redirect:" +userId +"/userEditAdmin";
            }
        }
        usr.setUsername(username);
        usr.setRealname(realname);
        usr.setMail(mail);
        userService.update(usr);
        return  "redirect:"+userId+"/userProfileAdmin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"/makeAdmin"},method = RequestMethod.POST)
    public String makeAdmin(@RequestParam String userid, @RequestParam Map<String,String> form) throws NotFoundException {
        User user = userService.getUserId(Long.valueOf(userid));
        user.getRoles().add(Role.ADMIN);

        userService.update(user);
        return "redirect:/userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"/movieAdmin"},method = RequestMethod.GET)
    public String movieAdmin(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "movieAdmin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"/addMovieAdmin"},method = RequestMethod.GET)
    public String addMovieAdmin(Model model) {
        model.addAttribute("producers", producerRepository.findAll());
        return "addMovieAdmin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"/addMovie"},method = RequestMethod.POST)
    public String addMovieAdminForm(@RequestParam String name, @RequestParam String releaseDate,
                                    @RequestParam String description, @RequestParam String duration, @RequestParam String budget,@RequestParam String producerName,Model model) {

        if (movieRepository.findByName(name) != null) {
            model.addAttribute("message","Name film already exists!");
            return "addMovieAdmin";
        }
        if (producerRepository.findByName(producerName) == null ) {
            model.addAttribute("message","Producer not found!");
            return "addMovieAdmin";
        }
        Movie movie = new Movie(name,releaseDate,description,duration,budget,producerRepository.findByName(producerName), "null");
        movieRepository.save(movie);
        return "redirect:/movieAdmin";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"/producerAdmin"}, method = RequestMethod.GET)
    public String producerAdmin(Model model) {
        model.addAttribute("producers", producerRepository.findAll());
        return "producerAdmin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"/addProducerAdmin"}, method = RequestMethod.GET)
    public String addProducerAdmin(Model model) {
        return "addProducerAdmin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"/addProducer"}, method = RequestMethod.POST)
    public String addProducerAdminForm(@RequestParam String name, @RequestParam String country, @RequestParam String date, Model model) {
        if (producerRepository.findByName(name) != null) {
            model.addAttribute("message","Producer already exists!");
            return "addProducerAdmin";
        }
        Producer producer = new Producer(name,country,date);
        producerRepository.save(producer);
        return "redirect:/producerAdmin";
    }


    @RequestMapping(value = {"/movierInfoAdmin/{movieid}"}, method = RequestMethod.GET)
    public String getMovieInfoAdmin(@PathVariable String movieid, Model model) throws NotFoundException {
        Movie movie = movieService.getMovieId(Integer.parseInt(movieid));
        Set<Actor> actors = movie.getActors();
        model.addAttribute("movies",movie);
        model.addAttribute("actors",actors);
        return "movierInfoAdmin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"{movieid}/movierEditAdmin"}, method = RequestMethod.GET)
    public String getMovieEditAdmin(@PathVariable String movieid, Model model) throws NotFoundException {
        Movie movie = movieService.getMovieId(Integer.parseInt(movieid));
        model.addAttribute("movies",movie);
        return "movierEditAdmin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "movieEdit" ,method = RequestMethod.POST)
    public String getMovieEditAdminForm(@RequestParam String name, String releaseDate,String description, String duration, String budget, @RequestParam String movieId,Model model) throws NotFoundException {
        Movie movie = movieService.getMovieId(Integer.parseInt(movieId));
        if (!movie.getName().equals(name)) {
            if (movieRepository.findByName(name) != null) {
                model.addAttribute("message","Name already exists!");
                return  "redirect:" +movieId +"/movierEditAdmin";
            }
        }
        movie.setName(name);
        movie.setReleaseDate(releaseDate);
        movie.setDescription(description);
        movie.setDuration(duration);
        movie.setBudget(budget);
        movieService.update(movie);
        return  "redirect:/movierInfoAdmin/"+movieId;
    }

    @RequestMapping(value = {"/producerInfoAdmin/{producerid}"}, method = RequestMethod.GET)
    public String getProducerInfoAdmin(@PathVariable String producerid, Model model) throws NotFoundException {
        Producer producer = producerService.getProducerId(Integer.parseInt(producerid));
        List<Genres> genres = producer.getGenres();
        Set<Movie> movies = producer.getMovies();
        model.addAttribute("producers", producer);
        model.addAttribute("genres", genres);
        model.addAttribute("movies", movies);
        return "producerInfoAdmin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"{producerid}/producerEditAdmin"}, method = RequestMethod.GET)
    public String getProducerEditAdmin(@PathVariable String producerid, Model model) throws NotFoundException {
        Producer producer = producerService.getProducerId(Integer.parseInt(producerid));
        model.addAttribute("producers",producer);
        return "producerEditAdmin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "producerEdit", method = RequestMethod.POST)
    public String getProducerEditAdminForm(@RequestParam String name, String country, String date, @RequestParam String producerId,Model model) throws NotFoundException {
        Producer producer = producerService.getProducerId(Integer.parseInt(producerId));
        if (!producer.getName().equals(name)) {
            if (producerRepository.findByName(name) != null) {
                model.addAttribute("message","Producer already exists!");
                return "redirect:" + producerId+"/producerEditAdmin";
            }
        }
        producer.setName(name);
        producer.setCountry(country);
        producer.setDate(date);
        producerService.update(producer);
        return "redirect:/producerInfoAdmin/"+producerId;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/actorAdmin",method = RequestMethod.GET)
    public String actorAdmin(Model model){
        model.addAttribute("actors", actorRepository.findAll());
        return "actorAdmin";
    }

    @RequestMapping(value = "/actorInfoAdmin/{actorid}",method = RequestMethod.GET)
    public String getActorInfoAdmin(@PathVariable String actorid, Model model) throws NotFoundException {
        Actor actor = actorService.getActorId(Integer.parseInt(actorid));
        Set<Movie> movies = actor.getMovie();
        List<Genres> genres = actor.getGenres();
        model.addAttribute("actors", actor);
        model.addAttribute("movies",movies);
        model.addAttribute("genres",genres);
        return "actorInfoAdmin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "addActorAdmin", method = RequestMethod.GET)
    public String addActorAdmin() {
        return "addActorAdmin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"/addActor"}, method = RequestMethod.POST)
    public String addActorAdminForm(@RequestParam String name, @RequestParam String country, @RequestParam String date,Model model) {
        if (actorRepository.findByName(name)!= null) {
            model.addAttribute("message","Actor name already exists!");
            return "addActorAdmin";
        }
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "actorEdit", method = RequestMethod.POST)
    public String getActorEditAdminForm(@RequestParam String name, String country, String date, @RequestParam String actorId,Model model) throws NotFoundException {
        Actor actor = actorService.getActorId(Integer.parseInt(actorId));

        if (!actor.getName().equals(name)) {
            if (actorRepository.findByName(name) != null){
                model.addAttribute("message","Actor name already exists!");
                return  "redirect:" + actorId+"/actorEditAdmin";
            }
        }
        actor.setName(name);
        actor.setCountry(country);
        actor.setDate(date);
        actorService.save(actor);
        return "redirect:/actorInfoAdmin/" +  actorId;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"/addMovieActors/{movieid}"}, method = RequestMethod.GET)
    public String addMovieActor(@PathVariable String movieid, Model model) throws NotFoundException {
        Movie movie = movieService.getMovieId(Integer.parseInt(movieid));
        model.addAttribute("movies",movie);
        model.addAttribute("actors", actorRepository.findAll());
        return "addMovieActors";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"/addMovieActors/{movieid}/{actorid}"}, method = RequestMethod.GET)
    public String addMovieActorForm(@PathVariable("movieid") String movieid, @PathVariable("actorid") String actorid,Model model) throws NotFoundException {
        Movie movie = movieService.getMovieId(Integer.parseInt(movieid));

        if (movie.getActors().contains(actorRepository.findById(Integer.parseInt(actorid)))){
            model.addAttribute("message","The actor is already in the film!");
            return "redirect:/addMovieActors/" + movieid;
        }
        movie.getActors().add(actorRepository.findById(Integer.parseInt(actorid)));
        movieService.update(movie);
        return "redirect:/addMovieActors/" + movieid;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/genresAdmin",method = RequestMethod.GET)
    public String genresAdmin(Model model){
        model.addAttribute("genres", genresRepository.findAll());
        return "genresAdmin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/addGenresAdmin",method = RequestMethod.GET)
    public String addGenresAdmin(Model model){
        return "addGenresAdmin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/addGenre",method = RequestMethod.POST)
    public String addGenresAdminForm(@RequestParam String name, Model model) throws Exception {

        if (genresRepository.findBygenreName(name) != null) {
            model.addAttribute("message","Genres already exists");
            return "addGenresAdmin";
        }

        Genres genres = new Genres(name);
        genresService.save(genres);

        return "redirect:/genresAdmin";
    }

    @RequestMapping(value = {"/genresInfoAdmin/{genreid}"},method = RequestMethod.GET)
    public String genresInfoAdmin(@PathVariable String genreid, Model model) throws Exception {
        Genres genres = genresService.getGenresId(Integer.parseInt(genreid));
        Set<Movie> movies = genres.getMovies();
        Set<Actor> actors = genres.getActors();
        Set<Producer> producers = genres.getProducers();

        model.addAttribute("genres", genres);
        model.addAttribute("movies", movies);
        model.addAttribute("actors", actors);
        model.addAttribute("producers", producers);
        model.addAttribute("countActors", sqlService.countActorsGenres(genres.getId()));
        model.addAttribute("countMovies", sqlService.countMoviesGenres(genres.getId()));
        model.addAttribute("countProducers", sqlService.countProducersGenres(genres.getId()));
        return "genresInfoAdmin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"{genreid}/genresEditAdmin"},method = RequestMethod.GET)
    public String genresEditAdmin(@PathVariable String genreid, Model model) throws Exception {
        Genres genres = genresService.getGenresId(Integer.parseInt(genreid));
        model.addAttribute("genres", genres);
        return "genresEditAdmin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "genreEdit", method = RequestMethod.POST)
    public String getGenresEditAdminForm(@RequestParam String name, @RequestParam String genreId, Model model) throws Exception {
        if (genresRepository.findBygenreName(name) != null) {
            model.addAttribute("message","Genres already exists");
            return "redirect:" + genreId +"/genresEditAdmin";
        }

        Genres genres = genresService.getGenresId(Integer.parseInt(genreId));
        genres.setGenreName(name);
        genresService.save(genres);

        return "redirect:/genresInfoAdmin/"+ genreId;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"/addMovieGenres/{movieid}"}, method = RequestMethod.GET)
    public String addMovieGenres(@PathVariable String movieid, Model model) throws NotFoundException {
        Movie movie = movieService.getMovieId(Integer.parseInt(movieid));
        model.addAttribute("movies",movie);
        model.addAttribute("genres",genresRepository.findAll());
        return "addMovieGenres";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"/addMovieGenres/{movieid}/{genreid}"}, method = RequestMethod.GET)
    public String addMovieGenreForm(@PathVariable("movieid") String movieId, @PathVariable("genreid") String genreid) throws Exception {
        Movie movie = movieService.getMovieId(Integer.parseInt(movieId));
        List<Genres> genresList = movie.getGenres();

        if (genresList.contains(genresRepository.findById(Integer.parseInt(genreid)))){
            return "redirect:/addMovieGenres/" + movieId;
        }

        movie.getGenres().add(genresRepository.findById(Integer.parseInt(genreid)));
        movieService.update(movie);
        return "redirect:/addMovieGenres/" + movieId;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"/addActorGenres/{actorid}"}, method = RequestMethod.GET)
    public String addActorGenres(@PathVariable String actorid, Model model) throws NotFoundException {
        Actor actor = actorService.getActorId(Integer.parseInt(actorid));
        model.addAttribute("actors",actor);
        model.addAttribute("genres",genresRepository.findAll());
        return "addActorGenres";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"addActorGenres/{actorid}/{genreid}"}, method = RequestMethod.GET)
    public String addActorGenreForm(@PathVariable("actorid") String actorid, @PathVariable("genreid") String genreid) throws Exception {
        Actor actor = actorService.getActorId(Integer.parseInt(actorid));
        List<Genres> genresList = actor.getGenres();

        if (genresList.contains(genresRepository.findById(Integer.parseInt(genreid)))) {
            return "redirect:/addActorGenres/" + actorid;
        }

        actor.getGenres().add(genresRepository.findById(Integer.parseInt(genreid)));
        actorService.save(actor);
        return "redirect:/addActorGenres/" + actorid;
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"/addProducerGenres/{producerid}"}, method = RequestMethod.GET)
    public String addProducerGenres(@PathVariable String producerid, Model model) throws NotFoundException {
        Producer producer = producerService.getProducerId(Integer.parseInt(producerid));
        model.addAttribute("producers",producer);
        model.addAttribute("genres",genresRepository.findAll());
        return "addProducerGenres";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"/addProducerGenres/{producerid}/{genreid}"}, method = RequestMethod.GET)
    public String addProducerGenreForm(@PathVariable("producerid") String producerid, @PathVariable("genreid") String genreid,Model model) throws Exception {
        Producer producer = producerService.getProducerId(Integer.parseInt(producerid));
        List<Genres> genresList = producer.getGenres();

        if (genresList.contains(genresRepository.findById(Integer.parseInt(genreid)))){
            model.addAttribute("message","Genres already exists in producer!");
            return "redirect:/addProducerGenres/" + producerid;
        }

        producer.getGenres().add(genresRepository.findById(Integer.parseInt(genreid)));
        producerService.update(producer);

        return "redirect:/addProducerGenres/" + producerid;
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/dataParsing",method = RequestMethod.GET)
    public String parsing(Model model){
        return "dataParsing";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"/dataParsing"}, method = RequestMethod.POST)
    public String parsing(@RequestParam String start, Map<String, Object> model) {

        if (start.isEmpty() || start.equals(" ")) {
            model.put("message","Incorrect info!");
            return "dataParsing";
        }

        ParserService parserService = new ParserService();
        for (int i = Integer.parseInt(start); i < Integer.parseInt(start)+1; i++) {
            try {
                String[] res = parserService.parsePage(i).split("\\|");
                if(!res[0].equals("null") && movieRepository.findByName(res[0]) == null) {
                    if (producerRepository.findByName(res[10]) == null) {
                        String[] prod = parserService.parsDirector(res[11]).split("\\|");
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

                    Movie movie = new Movie(res[0], res[4], res[8], res[2], res[3], producerRepository.findByName(res[10]), res[5]);

                    String[] actors = res[9].split(",");
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

                        String[] genres = res[7].split(",");
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
                            if(!producerRepository.findByName(res[10]).getGenres().contains(genres1)){
                                Producer producer = producerRepository.findByName(res[10]);
                                producer.addGenres(genres1);
                                producer.addMovies(movie);
                                producerRepository.save(producer);
                            }
                        }
                        actor.addMovie(movie);
                        actorRepository.save(actor);
                        movie.addActor(actor);
                    }

                    String [] ratings = res[6].split(",");
                    if (ratings[3] != "-") {
                        movie.setRating(Double.parseDouble(ratings[3]));
                    }
                    else {
                        movie.setRating(0);
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"/integration"}, method = RequestMethod.GET)
    public String integration(){
        return "/integration";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = {"/integrationSQL"}, method = RequestMethod.POST)
    public String getResult(@RequestParam String sql,Model model) {

        if (sql.isEmpty() || sql == " "){
            model.addAttribute("message","Sql uncorrect!");
        }

        String [] res = sql.split(" ");
        if (sqlService.sqlCheck(res[0])) {
            if (res[0].equals("SELECT") || res[0].equals("Select") || res[0].equals("select")) {
                model.addAttribute("sql", sql);
                model.addAttribute("answers", sqlService.selectData(sql));
                return "integration";
            }
            if (res[0].equals("INSERT") || res[0].equals("Insert") || res[0].equals("insert")) {
                sqlService.insertData(sql);
                model.addAttribute("sql", sql);
                model.addAttribute("answers", sql);
                return "integration";
            }
            if (res[0].equals("UPDATE") || res[0].equals("Update") || res[0].equals("update")) {
                sqlService.updateData(sql);
                model.addAttribute("sql", sql);
                model.addAttribute("answers", sql);
                return "integration";
            }
        }
        model.addAttribute("message","Incorrect!");
        return "integration";
    }




}
