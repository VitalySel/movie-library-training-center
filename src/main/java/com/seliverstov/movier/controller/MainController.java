package com.seliverstov.movier.controller;

import com.seliverstov.movier.domain.Movie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class MainController {

    @RequestMapping(value="/",method = RequestMethod.GET)
    public String main(Map<String, Object> model) {
        return "index";
    }
}
