package com.seliverstov.movier.controller;

import com.seliverstov.movier.domain.Role;
import com.seliverstov.movier.domain.User;
import com.seliverstov.movier.repository.UserRepository;
import com.seliverstov.movier.service.UserService;
import com.sun.org.apache.regexp.internal.RE;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

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


}
