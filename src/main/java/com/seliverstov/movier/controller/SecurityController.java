package com.seliverstov.movier.controller;

import com.seliverstov.movier.domain.User;
import com.seliverstov.movier.repository.UserRepository;
import com.seliverstov.movier.service.MailService;
import com.seliverstov.movier.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@Controller
public class SecurityController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;

    @RequestMapping(value="/login")
    public String login(){
        return "/login";
    }

    @GetMapping(value = "/forgot")
    public String getForgotPage() {
        return "/forgot";
    }

    @PostMapping("/forgot")
    public String getForgotMail(@RequestParam String email){
        User user = userRepository.findByMail(email);
        user.setActivationCode(UUID.randomUUID().toString());
        userService.update(user);
        String message =String.format("Hello there! You've(or not you) just send us a request for your password \n" +
        "visit next link to enter new password \n" +
                " http://localhost:8080/forgot/%s" +
                " if it wasn't just ignore this message \n" +
                "Good luck",
                user.getActivationCode());
        mailService.send(email,"Forgot your password",message);
        return "redirect:/";
    }

    @GetMapping("/forgot/{code}")
    public String getPasswordChangePage(@PathVariable String code,
                                        Model model){
        model.addAttribute("code",code);
        return "newPassword";
    }

    @PostMapping("/forgot/{code}")
    public String setNewPassword(@PathVariable String code,@RequestParam String password,@RequestParam String hash) {
        User user =userService.findByActivationCode(hash);
        user.setPassword(password);
        userService.update(user);
        return "redirect:/";

    }

    @GetMapping(value = "/registration")
    public String getUser(Model model) {
        model.addAttribute("newUser",new User());
        return "/registration";
    }

    @PostMapping(value = "/registration")
    public String setUser(@ModelAttribute User user , Map<String,Object> model){
        User useFromDb = userRepository.findByUsername(user.getUsername());

        if (useFromDb != null) {
            model.put("message","E-mail уже существует");
            return "registration";
        }

        user.setActivationCode(UUID.randomUUID().toString());
        userService.addUser(user);
        return "redirect:/login";
    }

    @GetMapping(value = "/activate/{code}")
    public String activate(@PathVariable String code) {
        userService.activateUser(code);
        return "login";
    }

}
