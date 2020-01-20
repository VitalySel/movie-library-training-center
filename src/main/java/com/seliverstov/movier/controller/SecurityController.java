package com.seliverstov.movier.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {

    @RequestMapping(value = "/login")
    public String login() {
        return "/login";
    }

    @GetMapping(value = "/forgot")
    public String getForgotPage() {
        return "/forgot";
    }
    @PostMapping("/forgot")
    public String getForgotMail(){
        return "redirect:/";
    }

    @GetMapping(value = "/registration")
    public String getUser() {
        return "/registration";
    }
}
