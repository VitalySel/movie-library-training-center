package com.seliverstov.movier.controller;

import com.seliverstov.movier.domain.Role;
import com.seliverstov.movier.domain.User;
import com.seliverstov.movier.repository.UserRepository;
import com.seliverstov.movier.service.UserService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Value("${upload.path}")
    private String uploadPath;

    @RequestMapping(value = "/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping(value = {"/userList"}, method = RequestMethod.GET)
    public String getUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "userList";
    }

    @RequestMapping(value = {"/userProfileAdmin"}, method = RequestMethod.GET)
    public String getUserProfileAdmin(@RequestParam String userid, Model model){
        model.addAttribute("users", userid);
        return "userProfileAdmin";
    }


}
