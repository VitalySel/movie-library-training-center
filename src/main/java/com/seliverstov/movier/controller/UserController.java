package com.seliverstov.movier.controller;

import com.seliverstov.movier.domain.User;
import com.seliverstov.movier.repository.UserRepository;
import com.seliverstov.movier.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping(value = "/profile")
    public String profile(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("users",user);
        return "profile";
    }
}
